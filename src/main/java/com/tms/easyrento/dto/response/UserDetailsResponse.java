package com.tms.easyrento.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/29/24 5:54 PM
 */

@Getter
@Setter
public class UserDetailsResponse {
    private Long ownerId;
    private Long tenantId;
    private String username;
    private String email;
}
