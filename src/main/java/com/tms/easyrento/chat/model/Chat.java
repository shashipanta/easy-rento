package com.tms.easyrento.chat.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-09-03 23:34
 */

@Document(collection = "chats")
public class Chat {

    @Id
    private String id;

    private List<Long> participants;

    private LastMessage lastMessage;

    private Map<Long, Integer> unread;

    public static class LastMessage {
        private String content;
        private Long senderId;
        private String timestamp; // ISO date string
    }
}
