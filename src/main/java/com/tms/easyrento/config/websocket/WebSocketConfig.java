package com.tms.easyrento.config.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final String[] allowedOrigins = {
            "http://127.0.0.1:5173",
            "http://localhost:5173",
            "http://localhost:5174",
            "http://127.0.0.1:5174",
            "http://localhost:3000",
    };

    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;
    /*
        Use of /app: all the messages will be routed to methods annotated
        with @MessageMapping()
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.enableSimpleBroker("/topic/private", "/topic/group");
        config.setApplicationDestinationPrefixes("/app");
    }

    /*
        Register endpoints that the clients use to connect to the server
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(jwtHandshakeInterceptor)
                .setAllowedOrigins(allowedOrigins);
//                .withSockJS();      // fallback for browsers not supporting web socket
    }
}
