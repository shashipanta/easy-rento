package com.tms.easyrento.service;

import com.tms.easyrento.dto.request.LoginRequest;
import com.tms.easyrento.dto.request.UserRequest;
import com.tms.easyrento.dto.response.LoginResponse;
import com.tms.easyrento.dto.response.UserDetailsResponse;
import com.tms.easyrento.dto.response.UserResponse;
import com.tms.easyrento.model.auth.UserAccount;

import java.util.List;
import java.util.Optional;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/5/23 11:57 PM
 */
public interface UserAccountService {

    Long create(UserRequest request);

    Long update(Long id, UserRequest request);

    List<UserResponse> listAll(Long isActive);

    UserResponse getById(Long id);

    void softDelete(Long id, Long isActive);

    Optional<UserAccount> fetchUserAccountByUsername(String username);

    LoginResponse generateToken(LoginRequest loginRequest);

    Long getId(String username);

    UserDetailsResponse details(String token);
}
