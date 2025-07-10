package com.tms.easyrento.dbMappers;

import com.tms.easyrento.admin.UserAccountDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-04 17:16
 */

@Mapper
public interface UserAccountMapper {

    Optional<UserAccountDto> findUserAccountBy(String username);

    Optional<UserAccountDto> findAdminUserAccountBy(String username);
}
