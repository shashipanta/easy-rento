package com.tms.easyrento.chat.controller;

import com.tms.easyrento.chat.MessageType;
import com.tms.easyrento.chat.dto.ChatRequest;
import com.tms.easyrento.chat.dto.ChatResponse;
import com.tms.easyrento.chat.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-23 06:20
 */

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Handles the request sent from client to /<prefix>/chat.send
     * Here message sent to /app/chat.send
     */
    @MessageMapping("/chat.send")
    public void sendMessage(@Payload @Valid ChatRequest request,
                            SimpMessageHeaderAccessor headerAccessor) {

        Long senderId = (Long) headerAccessor.getSessionAttributes().get("userId");

        request.setSenderId(senderId);
//        request.setTimestamp(Instant.now());

        ChatResponse chatResponse = chatService.saveMessage(request);

        // dynamically send message to client: similar to @SendTo
        if (request.getMessageType() == MessageType.PRIVATE) {
            messagingTemplate.convertAndSend("/topic/private/" + request.getReceiverId(), chatResponse);
        } else if (request.getMessageType() == MessageType.GROUP) {
            messagingTemplate.convertAndSend("/topic/group/" + request.getGroupId(), chatResponse);
        }
    }

}
