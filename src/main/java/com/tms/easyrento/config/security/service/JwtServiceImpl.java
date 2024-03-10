package com.tms.easyrento.config.security.service;

import com.tms.easyrento.config.security.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/29/24 2:23 PM
 */

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtUtils jwtUtils;
    @Override
    public String extractClaim(String token, String claimName) {
        Object o = jwtUtils.customClaims(claimName, token);
        return o.toString();
    }

    @Override
    public String extractUserId(String token, String claimName) {
        return jwtUtils.customClaims(claimName, token);
    }


}
