package com.tms.easyrento.config.security.authority;

import com.tms.easyrento.repository.UserAccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collection;

/**
 * @author shashi
 * @version 1.0.0
 * @since 3/29/24 9:08 AM
 */

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final UserAccountRepository userAccountRepository;

    public AuthorizationInterceptor(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()) return false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return true;
    }
}
