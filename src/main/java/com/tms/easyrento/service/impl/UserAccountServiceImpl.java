package com.tms.easyrento.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.easyrento.config.security.util.JwtUtils;
import com.tms.easyrento.dto.request.LoginRequest;
import com.tms.easyrento.dto.request.OwnerRequest;
import com.tms.easyrento.dto.request.TenantRequest;
import com.tms.easyrento.dto.request.UserRequest;
import com.tms.easyrento.dto.response.LoginResponse;
import com.tms.easyrento.dto.response.UserDetailsResponse;
import com.tms.easyrento.dto.response.UserResponse;
import com.tms.easyrento.enums.UserType;
import com.tms.easyrento.mappers.UserAccountMapper;
import com.tms.easyrento.model.auth.UserAccount;
import com.tms.easyrento.repository.UserAccountRepository;
import com.tms.easyrento.service.OwnerService;
import com.tms.easyrento.service.TenantService;
import com.tms.easyrento.service.UserAccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private final TenantService tenantService;

    private final UserAccountRepository userAccountRepo;
    private final AuthenticationManager authenticationManager;
    private final UserAccountMapper userAccountMapper;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final ObjectMapper objectMapper;

    @Override
    public Long create(UserRequest request) {
        UserAccount userAccount = userAccountMapper.requestToEntity(request);
        // password encryption
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));

        // create owner or tenant account based on portal
        String userType = request.getUserType().name();

        if(UserType.OWNER.name().equals(userType)) {
            OwnerRequest ownerRequest = objectMapper.convertValue(request, OwnerRequest.class);
            ownerRequest.setName(request.getUsername());
            ownerRequest.setNameNp("nepali_name");
        } else {
            TenantRequest tenantRequest = objectMapper.convertValue(request, TenantRequest.class);
            tenantRequest.setName(request.getUsername());
            tenantRequest.setNameNp("nepali_name");
        }
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

        return userAccountRepo.findBy(username);
    }

    @Override
    public LoginResponse generateToken(LoginRequest loginRequest) {
        // authenticate user
        Authentication authentication = authenticateUser(loginRequest);

        // token generation
        String token = jwtUtils.generateToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserAccount userAccount = fetchUserAccountByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return LoginResponse.builder()
                .refreshToken("refresh_token")
                .userId(userAccount.getId())
                .accessToken(token)
                .expiryTime("expiry_time")
                .build();
    }

    @Override
    public Long getId(String username) {
        return userAccountRepo.findBy(username)
                .orElseThrow(EntityNotFoundException::new).getId();
    }

    @Override
    public UserDetailsResponse details(String token) {
        Object o = jwtUtils.customClaims("userId", token);
        UserAccount userAccount = userAccountRepo.findById(Long.valueOf(o.toString())).orElseThrow();
        UserDetailsResponse userDetailsResponse = objectMapper.convertValue(userAccount, UserDetailsResponse.class);
        String userType = userAccount.getUserType().name();
        if(UserType.OWNER.name().equals(userType))
            userDetailsResponse.setOwnerId(userAccount.getId());
        else
            userDetailsResponse.setTenantId(userAccount.getId());

        return userDetailsResponse;
    }

    public Authentication authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        return authentication;
    }
}
