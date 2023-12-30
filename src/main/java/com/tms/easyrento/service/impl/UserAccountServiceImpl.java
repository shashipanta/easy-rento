package com.tms.easyrento.service.impl;

import com.tms.easyrento.dto.request.UserRequest;
import com.tms.easyrento.dto.response.UserResponse;
import com.tms.easyrento.mappers.UserAccountMapper;
import com.tms.easyrento.model.UserAccount;
import com.tms.easyrento.repository.UserAccountRepository;
import com.tms.easyrento.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/6/23 12:00 AM
 */

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepo;
    private final UserAccountMapper userAccountMapper;
    @Override
    public Long create(UserRequest request) {
        UserAccount userAccount = userAccountMapper.requestToEntity(request);
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
}
