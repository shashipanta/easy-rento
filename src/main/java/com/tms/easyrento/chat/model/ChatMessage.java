package com.tms.easyrento.chat.model;

import com.tms.easyrento.chat.MessageStatus;
import com.tms.easyrento.chat.MessageType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-22 01:01
 */

@Getter
@Setter
@Builder
@Document(collection = "conversations")
public class ChatMessage {

    @Id
    private String id;

    @Field(name = "sender_id", write = Field.Write.NON_NULL)
    private Long senderId;

    @Field(name = "receiver_id", write = Field.Write.NON_NULL)
    private Long receiverId; // for 1-1 chats or group ID

    private String groupId; // null if private

    @Field(name = "content", write = Field.Write.NON_NULL)
    private String content;

    @Enumerated(EnumType.STRING)
    @Field(name = "message_type", write = Field.Write.NON_NULL)
    private MessageType type; // CHAT, JOIN, LEAVE

    @Field(name = "time_stamp")
    private LocalDateTime timestamp;

    @Field(name = "edited")
    private boolean edited = false;

    @Enumerated(EnumType.STRING)
    @Field(name = "status", write = Field.Write.NON_NULL)
    private MessageStatus status;

}
