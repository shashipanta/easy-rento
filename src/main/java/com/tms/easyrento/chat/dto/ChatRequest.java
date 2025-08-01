package com.tms.easyrento.chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tms.easyrento.chat.MessageType;
import com.tms.easyrento.constants.FieldErrorConstants;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-23 06:28
 */

@Getter
@Setter
public class ChatRequest {

    private String id;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    @JsonProperty("senderId")
    private Long senderId;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    @JsonProperty("receiverId")
    private Long receiverId;

    @JsonProperty("groupId")
    private String groupId;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    @JsonProperty("type")
    private MessageType messageType;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    @JsonProperty("content")
    private String messageContent;
}
