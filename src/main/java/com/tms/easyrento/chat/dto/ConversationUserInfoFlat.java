package com.tms.easyrento.chat.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-10-31 02:37
 */

@Getter
@Setter
public class ConversationUserInfoFlat {
    private Long senderUserId;
    private String senderUserName;
    private Long receiverUserId;
    private String receiverUserName;
}
