package com.tms.easyrento.chat.service;

import com.tms.easyrento.chat.dto.FriendshipDto;
import com.tms.easyrento.chat.dto.ChatRequest;
import com.tms.easyrento.chat.dto.ChatResponse;
import com.tms.easyrento.chat.dto.ChatResponseDto;

import java.util.List;

public interface ChatService {

        ChatResponse saveMessage(ChatRequest chatRequest);

        List<ChatResponse> getMessagesForUser(Long userId);

        List<ChatResponseDto> getRecentMessages(Long userId, Long groupId, int limit);

        List<FriendshipDto> getFriends(Long userId);

        List<ChatResponse> getGroupMessages(Long groupId);
}
