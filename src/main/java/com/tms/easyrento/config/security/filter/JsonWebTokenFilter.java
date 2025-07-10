package com.tms.easyrento.config.security.filter;

import com.tms.easyrento.config.security.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/11/24 3:02 AM
 */

@Component
@RequiredArgsConstructor
public class JsonWebTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AntPathMatcher antPathMatcher;

    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_COOKIE_HEADER_NAME = "accessToken";
    private static final String BEARER = "Bearer";
    private static final List<String> WHITELISTED_API = List.of(
            "/admin/login",
            "/admin/login?error", // this can be replaced with /admin/login/** as
            "/admin/css/**",
            "/admin/js/**",
            "/admin/**"           // any URI prefixing with /admin/login, /admin/dashboard, /admin/policies is MVC
    );

    private static final Logger logger = LoggerFactory.getLogger(JsonWebTokenFilter.class);

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String currentPath = request.getServletPath();
        return WHITELISTED_API.stream()
                .anyMatch(allowed -> antPathMatcher.match(allowed, currentPath));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // there is no authorization token in case of preflight requests : OPTIONS
        if ("OPTIONS".equals(request.getMethod())) {
            logger.info("Preflight request: METHOD NAME : {}, REQUEST URI : {}", request.getMethod(), request.getRequestURI());
            filterChain.doFilter(request, response);
        }
        String token = extractToken(request);
        logger.info("Token received : {}", token);
        if (StringUtils.hasText(token)) {
            Authentication authentication = jwtUtils.getAuthentication(token);
            if (authentication != null) {
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);


    }

    /**
     * Extract token from either header or from cookie
     *
     * @param request
     * @return
     */
    private String extractToken(HttpServletRequest request) {
        // Extract and return the token from the Authorization header
        // ...
        String bearerToken = request.getHeader(AUTH_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        if (request.getCookies() != null) {
            String cookieAuthToken = null;
            cookieAuthToken = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(AUTH_COOKIE_HEADER_NAME))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
            return cookieAuthToken;
        }
        return null;
    }

}
