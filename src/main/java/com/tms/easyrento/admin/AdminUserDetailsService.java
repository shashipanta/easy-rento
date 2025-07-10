package com.tms.easyrento.admin;

import com.tms.easyrento.service.AdminUserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-07 01:01
 */

@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminUserAccountService adminUserAccountService;

    /**
     * This class separates the user finding logic for normal user (ones accessing with REST-API) from the user accessing
     * MVC admin panel (admin users)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccountDto userAccount = adminUserAccountService.findAdminUserAccountBy(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        List<GrantedAuthority> grantedAuthorities = userAccount.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User.builder()
                .username(userAccount.getEmail())
                .password(userAccount.getPassword())
                .authorities(grantedAuthorities)
                .build();
    }
}
