package com.tms.easyrento.config.db;

import com.tms.easyrento.config.security.JwtAuthenticationToken;
import com.tms.easyrento.config.security.service.JwtService;
import com.tms.easyrento.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/29/24 3:50 PM
 */
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<Long> {

    private final UserAccountService userAccountService;
    private final JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(AuditorAwareImpl.class);
    @Override
    public Optional<Long> getCurrentAuditor() {
        //todo: use spring security logged in user details
        JwtAuthenticationToken authentication;
        try {
            authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                String loggedUserId = jwtService.extractClaim(authentication.getToken(), "userId");
                logger.info("Authentication has something {} and loggedUser : {}", authentication, loggedUserId);
                return Optional.of(Long.valueOf(loggedUserId));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(0L);
        }

        return null;
    }
}
