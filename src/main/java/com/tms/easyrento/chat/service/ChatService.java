package com.tms.easyrento.chat.service;

import com.tms.easyrento.chat.dto.ChatRequest;
import com.tms.easyrento.chat.dto.ChatResponse;
import com.tms.easyrento.chat.projections.ChatResponseProjection;

import java.util.List;

public interface ChatService {

        ChatResponse saveMessage(ChatRequest chatRequest);

        List<ChatResponse> getMessagesForUser(Long userId);

        List<ChatResponseProjection> getRecentMessages(Long userId, Long groupId, int limit);

        List<ChatResponse> getGroupMessages(Long groupId);
}
