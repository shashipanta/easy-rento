package com.tms.easyrento.chat.dto;


import java.time.Instant;

public record FriendshipDto(
        Long userId,
        String userName,
        String avatarUrl,
        Instant since,
        Instant lastInteractedOn
) {
}
