package com.tms.easyrento.chat.controller;

import com.tms.easyrento.chat.dto.FriendshipDto;
import com.tms.easyrento.chat.dto.ChatResponseDto;
import com.tms.easyrento.chat.dto.FriendshipReqDto;
import com.tms.easyrento.chat.service.ChatService;
import com.tms.easyrento.chat.service.FriendshipService;
import com.tms.easyrento.config.security.service.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final FriendshipService friendshipService;
    private final JwtServiceImpl jwtServiceImpl;

    @GetMapping("/history")
    ResponseEntity<List<ChatResponseDto>> getUserChats(@RequestParam("userId") Long userId,
                                                       @RequestParam(value = "groupId", required = false) Long groupId,
                                                       @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<ChatResponseDto> messagesForUser = chatService.getRecentMessages(userId, groupId, limit);
        return ResponseEntity.ok(messagesForUser);
    }

    @GetMapping("/friends")
    ResponseEntity<List<FriendshipDto>> getFriends() {
        Long loggedInUserId = jwtServiceImpl.getLoggedUserId();
        List<FriendshipDto> messagesForUser = friendshipService.getFriends(loggedInUserId);
        return ResponseEntity.ok(messagesForUser);
    }

    @PostMapping("/add/friendship")
    ResponseEntity<ChatResponseDto> addFriendship(@RequestBody FriendshipReqDto friendshipReqDto) {
        friendshipService.addFriendship(friendshipReqDto.userId(), friendshipReqDto.friendId());
        return ResponseEntity.ok(null);
    }

}
