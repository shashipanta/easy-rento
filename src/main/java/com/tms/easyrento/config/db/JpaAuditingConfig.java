package com.tms.easyrento.config.db;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/5/23 11:46 PM
 */

@Component
@EnableJpaAuditing(auditorAwareRef = "auditingProvider")
public class JpaAuditingConfig {


    @Bean
    public AuditorAware<Long> auditingProvider() {
        //todo: use spring security logged in user details
        return () -> Optional.of(0L);
    }
}
