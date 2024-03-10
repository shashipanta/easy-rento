package com.tms.easyrento.config.security.util;

import com.tms.easyrento.config.security.CustomUserDetailsService;
import com.tms.easyrento.config.security.JwtAuthenticationToken;
import com.tms.easyrento.config.security.property.JwtProperty;
import com.tms.easyrento.service.UserAccountService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/14/24 7:26 PM
 */

@Component
public class JwtUtils {

    private final JwtProperty jwtProperty;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserAccountService userAccountService;

    JwtUtils(@Lazy JwtProperty jwtProperty,
             @Lazy CustomUserDetailsService customUserDetailsService,
             @Lazy UserAccountService userAccountService) {
        this.jwtProperty = jwtProperty;
        this.customUserDetailsService = customUserDetailsService;
        this.userAccountService = userAccountService;
    }

    private static String SECRET_KEY;
    private static int EXPIRATION_TIME = 864_000_000; // 10 days

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Long loggedUserId = userAccountService.getId(userDetails.getUsername());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        Map<String, Object> customClaims = Map.of("userId", loggedUserId);

        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setClaims(Map.of("sub", userDetails.getUsername(), "userId", loggedUserId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();

        return token;

    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperty.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = extractAllClaims(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
//        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
        return new JwtAuthenticationToken(token, userDetails.getAuthorities());
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperty.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // extract certain claims
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public <T> T customClaims(String claimName, String token) {
        return extractClaim(token, claims -> (T) claims.get(claimName) );
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtProperty.getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
