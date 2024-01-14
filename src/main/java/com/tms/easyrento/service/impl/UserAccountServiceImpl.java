package com.tms.easyrento.service.impl;

import com.tms.easyrento.config.security.util.JwtUtils;
import com.tms.easyrento.dto.request.LoginRequest;
import com.tms.easyrento.dto.request.UserRequest;
import com.tms.easyrento.dto.response.LoginResponse;
import com.tms.easyrento.dto.response.UserResponse;
import com.tms.easyrento.mappers.UserAccountMapper;
import com.tms.easyrento.model.UserAccount;
import com.tms.easyrento.repository.UserAccountRepository;
import com.tms.easyrento.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.channels.AcceptPendingException;
import java.util.List;
import java.util.Optional;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/6/23 12:00 AM
 */

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepo;
    private final AuthenticationManager authenticationManager;
    private final UserAccountMapper userAccountMapper;
    private final PasswordEncoder passwordEncoder;

    @Lazy private final JwtUtils jwtUtils;
    @Override
    public Long create(UserRequest request) {
        UserAccount userAccount = userAccountMapper.requestToEntity(request);
        // password encryption
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        return userAccountRepo.save(userAccount).getId();
    }

    @Override
    public Long update(Long id, UserRequest request) {
        return null;
    }

    @Override
    public List<UserResponse> listAll(Long isActive) {
        return userAccountRepo.getAll(isActive)
                .stream().map(userAccountMapper::entityToResponse)
                .toList();
    }

    @Override
    public UserResponse getById(Long id) {
        return userAccountMapper.entityToResponse(userAccountRepo.findById(id).orElseThrow());
    }

    @Override
    public void softDelete(Long id, Long isActive) {
        userAccountRepo.toggleIsActive(id, isActive != 0);
    }

    @Override
    public Optional<UserAccount> fetchUserAccountByUsername(String username) {

        return userAccountRepo.findUserAccountByUsername(username);
    }

    @Override
    public LoginResponse generateToken(LoginRequest loginRequest) {
        // authenticate user
        Authentication authentication = authenticateUser(loginRequest);

        // token generation
        String token = jwtUtils.generateToken(authentication);

        return LoginResponse.builder()
                .refreshToken(token)
                .accessToken("access_token")
                .expiryTime("expiry_time")
                .build();
    }

    public Authentication authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
