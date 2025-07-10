package com.tms.easyrento.service;

import com.tms.easyrento.admin.UserAccountDto;

import java.util.Optional;

public interface AdminUserAccountService {

    Optional<UserAccountDto> findAdminUserAccountBy(String username);
}
