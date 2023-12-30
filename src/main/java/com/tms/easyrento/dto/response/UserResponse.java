package com.tms.easyrento.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/5/23 11:44 PM
 */

@Setter
@Getter
@Builder
public class UserResponse {
    private Long id;

    private String username;

    private String email;

    private boolean isActive;

    private String status;

    private String createdOn;

}
