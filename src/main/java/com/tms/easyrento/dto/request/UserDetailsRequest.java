package com.tms.easyrento.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/29/24 5:58 PM
 */

@Getter
@Setter
public class UserDetailsRequest {
    private Long userId;

    private String authToken;
}
