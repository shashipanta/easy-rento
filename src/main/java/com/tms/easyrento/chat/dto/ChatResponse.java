package com.tms.easyrento.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-23 06:28
 */

@Getter
@Setter
public class ChatResponse {

    private String id;

    private Long senderId;

    private Long receiverId;

    private String messageContent;

    private String senderName;

    private String receiverName;

    private LocalDateTime createdAt;

}
