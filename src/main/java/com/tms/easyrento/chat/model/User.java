package com.tms.easyrento.chat.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-10-31 02:01
 */

@Document("users")
public class User {

    @Id
    private String id;

    @Field("email")
    private String email;

    @Field("user_id")
    private Long userId;

    @Field("name")
    private String name;

    @Field(name = "avatar_url")
    private String avatarUrl;
}
