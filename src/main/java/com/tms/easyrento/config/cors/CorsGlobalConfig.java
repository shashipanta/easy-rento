package com.tms.easyrento.config.cors;

import com.tms.easyrento.config.security.authority.AuthorizationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/8/23 12:23 AM
 */
@Configuration
@RequiredArgsConstructor
public class CorsGlobalConfig implements WebMvcConfigurer{

    private final AuthorizationInterceptor authorizationInterceptor;
    private final String[] allowedOrigins = {
            "http://127.0.0.1:5173",
            "http://localhost:5173",
            "http://localhost:5174",
            "http://127.0.0.1:5174",
            "http://localhost:3000",
    };

    /**
     * Configure "global" cross-origin request processing. The configured CORS
     * mappings apply to annotated controllers, functional endpoints, and static
     * resources.
     * <p>Annotated controllers can further declare more fine-grained config via
     * {@link CrossOrigin @CrossOrigin}.
     * In such cases "global" CORS configuration declared here is
     * {@link CorsConfiguration#combine(CorsConfiguration) combined}
     * with local CORS configuration defined on a controller method.
     *
     * @param registry
     * @see CorsRegistry
     * @see CorsConfiguration#combine(CorsConfiguration)
     * @since 4.2
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedHeaders("Authorization", "Content-Type")
                .exposedHeaders("Authorization")
                .allowCredentials(true)
                .allowedMethods("*");
        WebMvcConfigurer.super.addCorsMappings(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
