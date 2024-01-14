package com.tms.easyrento.config.security;

import com.tms.easyrento.model.UserAccount;
import com.tms.easyrento.repository.UserAccountRepository;
import com.tms.easyrento.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/11/24 12:05 AM
 */

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountService userAccountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountService.fetchUserAccountByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(userAccount.getEmail())
                .password(userAccount.getPassword())
                .roles("User_role")
                .build();
    }
}
