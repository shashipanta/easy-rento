package com.tms.easyrento.chat.controller;

import com.tms.easyrento.chat.projections.ChatResponseProjection;
import com.tms.easyrento.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-29 03:14
 */

@RestController
@RequestMapping("/api/v1/chats/")
@RequiredArgsConstructor
public class RestChatController {

    private final ChatService chatService;

    @GetMapping("/history")
    ResponseEntity<List<ChatResponseProjection>> getUserChats(@RequestParam("userId") Long userId,
                                                              @RequestParam(value = "groupId", required = false) Long groupId,
                                                              @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<ChatResponseProjection> messagesForUser = chatService.getRecentMessages(userId, groupId, limit);
        return ResponseEntity.ok(messagesForUser);
    }
}
