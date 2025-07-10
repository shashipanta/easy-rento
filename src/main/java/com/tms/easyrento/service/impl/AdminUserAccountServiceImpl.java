package com.tms.easyrento.service.impl;

import com.tms.easyrento.admin.UserAccountDto;
import com.tms.easyrento.dbMappers.UserAccountMapper;
import com.tms.easyrento.service.AdminUserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-07 01:20
 */

@Service
@RequiredArgsConstructor
public class AdminUserAccountServiceImpl implements AdminUserAccountService {

    // mybatis mapper not mapstructMapper
    private final UserAccountMapper userAccountMapper;

    @Override
    public Optional<UserAccountDto> findAdminUserAccountBy(String username) {
        return userAccountMapper.findAdminUserAccountBy(username);
    }
}
