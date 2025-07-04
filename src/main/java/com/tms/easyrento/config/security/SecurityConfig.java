package com.tms.easyrento.config.security;

import com.tms.easyrento.admin.CustomLoginFailureHandler;
import com.tms.easyrento.config.security.filter.JsonWebTokenFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.util.AntPathMatcher;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/11/24 1:48 AM
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JsonWebTokenFilter jsonWebTokenFilter;
    private final CustomLoginFailureHandler customLoginFailureHandler;

    private static final String ADMIN_ENDPOINT = "/admin/**";

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    SecurityConfig(@Lazy CustomUserDetailsService customUserDetailsService,
                   @Lazy JsonWebTokenFilter jsonWebTokenFilter,
                   CustomLoginFailureHandler customLoginFailureHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.jsonWebTokenFilter = jsonWebTokenFilter;
        this.customLoginFailureHandler = customLoginFailureHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * --- Admin Security: Form Login, Session, CSRF, etc. ---
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurity(HttpSecurity http) throws Exception {
        logger.info("Setting up Admin security configuration");
        http
                .securityMatcher(ADMIN_ENDPOINT)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/login", "admin/css/**", "/admin/js/**").permitAll()
                        .anyRequest().authenticated() // ← allow any authenticated user (even without role)
                )
                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/spring-security/login")
                        .defaultSuccessUrl("/admin/dashboard", true)
                        .failureHandler(customLoginFailureHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login?logout")
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .csrf(AbstractHttpConfigurer::disable);
//                .csrf(csrf -> csrf
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                );

        return http.build();

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        logger.info("Setting up API security configuration (JWT + stateless).");
        
        // todo: /pubic for public endpoints
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authz -> authz
                                .requestMatchers("api/v1/user-accounts/auth/**").permitAll()
                                .requestMatchers("api/v1/properties/get-info/**").permitAll()
                                .requestMatchers("api/v1/properties/get-image/**").permitAll()
                                .requestMatchers("/error").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jsonWebTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * {@link AuthenticationManager} given by ProviderManager is used to authenticate user's credentials
     * DaoAuthenticationProvider makes use of userDetailsService with passwordEncoder to authenticate
     *
     * @return
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }
}
