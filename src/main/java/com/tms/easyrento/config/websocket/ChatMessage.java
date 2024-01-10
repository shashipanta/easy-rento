package com.tms.easyrento.config.websocket;

import lombok.*;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/31/23 1:09 AM
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    private MessageType type;
    private String content;
    private String sender;

}
