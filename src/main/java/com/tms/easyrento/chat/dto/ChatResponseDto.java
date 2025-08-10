package com.tms.easyrento.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-08-10 23:00
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseDto {

    private String id;
    private String content;
    private LocalDateTime timestamp;
    private String type; // raw string from Mongo, not enum
    private String senderId;
    private String senderName;
    private String receiverId;
    private String receiverName;
    private Boolean edited;
}

