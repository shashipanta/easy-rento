package com.tms.easyrento.chat.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-10-22 09:59
 */

@Getter
@Setter
@Document(collection = "friendships")
public class Friendship {

    private String id;

    @Field(name = "user_id", write = Field.Write.NON_NULL)
    private Long userId;

    @Field(name = "friend_id", write = Field.Write.NON_NULL)
    private Long friendId;

    @Field("since")
    private Instant since;

    @Field("last_interacted_on")
    private Instant lastInteractedOn;

}
