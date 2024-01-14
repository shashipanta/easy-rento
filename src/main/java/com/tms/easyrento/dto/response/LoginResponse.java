package com.tms.easyrento.dto.response;

import lombok.*;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/11/24 1:18 AM
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private String expiryTime;
}
