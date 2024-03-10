package com.tms.easyrento.config.security.service;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/29/24 2:09 PM
 */
public interface JwtService {

    String extractClaim(String token, String claimName);

    String extractUserId(String token, String claimName);

    String extractClaimForLoggedInUser(String claimName);

    Long getLoggedUserId();
}
