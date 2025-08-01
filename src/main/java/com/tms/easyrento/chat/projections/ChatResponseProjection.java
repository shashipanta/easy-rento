package com.tms.easyrento.chat.projections;

import com.tms.easyrento.chat.MessageType;

import java.time.LocalDateTime;

public interface ChatResponseProjection {

    String getId();
    String getName();
    String getContent();
    LocalDateTime getTimestamp();
    MessageType getType();
    String getSenderId();
    String getReceiverId();
    String getSenderName();
    String getReceiverName();
    Boolean isEdited();
}
