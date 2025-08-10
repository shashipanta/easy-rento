package com.tms.easyrento.chat.projections;

import java.time.LocalDateTime;

public interface ChatResponseProjection {

    String getId();
    String getName();
    String getContent();
    LocalDateTime getTimestamp();
//    String getType();
    String getSenderId();
    String getReceiverId();
    String getSenderName();
    String getReceiverName();
    Boolean isEdited();
}
