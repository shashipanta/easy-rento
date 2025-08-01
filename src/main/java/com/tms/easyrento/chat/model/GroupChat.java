package com.tms.easyrento.chat.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-23 10:30
 */

@Document(collection = "groups")
public class GroupChat {
    @Id
    private String id;

    private String name;

    private List<String> memberIds;
}

