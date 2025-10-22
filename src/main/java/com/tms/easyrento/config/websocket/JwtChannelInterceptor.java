package com.tms.easyrento.config.websocket;

import com.tms.easyrento.config.security.service.JwtService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-08-10 23:50
 */

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;

    public JwtChannelInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        assert accessor != null;
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            List<String> authHeaders = accessor.getNativeHeader("Authorization");

            if (authHeaders == null || authHeaders.isEmpty()) {
                throw new AccessDeniedException("No Authorization header found");
            }

            String authHeader = authHeaders.get(0);

            if (!authHeader.startsWith("Bearer ")) {
                throw new AccessDeniedException("Invalid Authorization header format");
            }

            String token = authHeader.substring(7);

            if (!jwtService.validateToken(token)) {
                throw new AccessDeniedException("Invalid token");
            }

            long userId = Long.parseLong(jwtService.extractClaim(token, "userId"));

            // todo: create a Principal or custom object to hold user information
            Objects.requireNonNull(accessor.getSessionAttributes()).put("userId", userId);
        }
        return message;
    }
}
