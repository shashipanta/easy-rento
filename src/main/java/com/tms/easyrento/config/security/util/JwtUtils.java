package com.tms.easyrento.config.security.util;

import com.tms.easyrento.config.security.CustomUserDetails;
import com.tms.easyrento.config.security.CustomUserDetailsService;
import com.tms.easyrento.config.security.property.JwtProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.CodePointLength;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/14/24 7:26 PM
 */

@Component
public class JwtUtils {

    private final JwtProperty jwtProperty;
    private final CustomUserDetailsService customUserDetailsService;

    JwtUtils(@Lazy JwtProperty jwtProperty,
             @Lazy CustomUserDetailsService customUserDetailsService) {
        this.jwtProperty = jwtProperty;
        this.customUserDetailsService = customUserDetailsService;
    }

    private static String SECRET_KEY;
    private static Long EXPIRATION_TIME; // 10 days

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtProperty.getTokenExpirationInMinutes() * 1000);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperty.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = extractClaims(token);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperty.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
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
