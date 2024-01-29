package com.tms.easyrento.config.db;

import com.tms.easyrento.config.security.service.JwtService;
import com.tms.easyrento.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * @author shashi
 * @version 1.0.0
 * @since 12/5/23 11:46 PM
 */

@Component
@RequiredArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "auditingProvider")
public class JpaAuditingConfig {

    private final UserAccountService userAccountService;
    private final JwtService jwtService;

    @Bean
    public AuditorAware<Long> auditingProvider() {
        return new AuditorAwareImpl(userAccountService, jwtService);
    }
}
