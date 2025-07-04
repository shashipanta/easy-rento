package com.tms.easyrento.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-04 10:38
 */

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception
    ) throws IOException, ServletException {
        logger.info("URL authentication failure: " + request.getRequestURI());

        String errorMessage = switch (exception.getClass().getSimpleName()) {
            case "UsernameNotFoundException" -> exception.getMessage();
            case "BadCredentialsException" -> exception.getMessage();
            case "LockedException" -> exception.getMessage();
            case "CredentialsExpiredException" -> exception.getMessage();
            case "AccountExpiredException" -> exception.getMessage();
            default -> exception.getMessage();
        };

        logger.error(errorMessage);
        logger.info(exception.getMessage());

        // redirect to login with URL-safe error
        setDefaultFailureUrl("/admin/login?error=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8));
        super.onAuthenticationFailure(request, response, exception);
    }
}
