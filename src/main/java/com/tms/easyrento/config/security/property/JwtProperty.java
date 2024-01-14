package com.tms.easyrento.config.security.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/14/24 9:19 PM
 */

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperty {

//    Generate 256 bits (32 bytes) of random data
//    openssl rand -base64 32

    private String secretKey;

    private Integer tokenExpirationInMinutes;
}
