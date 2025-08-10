package com.tms.easyrento.chat.service.impl;

import com.tms.easyrento.chat.MessageType;
import com.tms.easyrento.chat.dto.ChatRequest;
import com.tms.easyrento.chat.dto.ChatResponse;
import com.tms.easyrento.chat.dto.ChatResponseDto;
import com.tms.easyrento.chat.model.ChatMessage;
import com.tms.easyrento.chat.repo.ChatMessageRepo;
import com.tms.easyrento.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-23 10:58
 */

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepo chatMessageRepo;

    @Override
    public ChatResponse saveMessage(ChatRequest chatRequest) {
        ChatMessage chatMessage = requestToEntity(chatRequest);
        chatMessage = chatMessageRepo.save(chatMessage);
        return entityToResponse(chatMessage);
    }

    private ChatResponse entityToResponse(ChatMessage chatMessage) {
        if (chatMessage == null) return null;
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setId(chatMessage.getId());
        chatResponse.setSenderId(Long.valueOf(chatMessage.getSenderId()));
        chatResponse.setReceiverId(Long.valueOf(chatMessage.getReceiverId()));
        chatResponse.setMessageContent(chatMessage.getContent());

        return chatResponse;
    }

    private ChatMessage requestToEntity(ChatRequest chatRequest) {
        if (chatRequest == null) return null;
        return ChatMessage.builder()
                .id(chatRequest.getId())
                .senderId(String.valueOf(chatRequest.getSenderId()))
                .receiverId(String.valueOf(chatRequest.getReceiverId()))
                .groupId(chatRequest.getGroupId())
                .type(chatRequest.getGroupId() != null ? MessageType.GROUP : MessageType.PRIVATE)
                .content(chatRequest.getMessageContent())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public List<ChatResponse> getMessagesForUser(Long userId) {
        List<ChatMessage> byReceiverId = chatMessageRepo.findByReceiverId(String.valueOf(userId));
        return byReceiverId.stream()
                .map(this::entityToResponse)
                .toList();
    }

    @Override
    public List<ChatResponseDto> getRecentMessages(Long userId, Long groupId, int limit) {
        List<ChatResponseDto> messages;
        Pageable pageable = PageRequest.of(0, limit);
        if (groupId != null) {
            // Get recent group messages
            messages = chatMessageRepo.findByGroupIdOrderByTimestampDesc(groupId, pageable);
        } else {
            // Get recent private messages where user is sender or receiver
            messages = chatMessageRepo.findPrivateMessageBys(userId);
        }

        // Reverse to show oldest first
        Collections.reverse(messages);

        return messages;
    }

    @Override
    public List<ChatResponse> getGroupMessages(Long groupId) {
        List<ChatMessage> byGroupId = chatMessageRepo.findByGroupId(String.valueOf(groupId));
        return byGroupId.stream()
                .map(this::entityToResponse)
                .toList();
    }
}
