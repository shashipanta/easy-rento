package com.tms.easyrento.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-04 17:06
 */

@Getter
@Setter
public class UserAccountDto {

    private Long id;

    private String userName;

    private String password;

    private String email;

    private String userType;

    private Set<RoleDto> roles = new HashSet<>();
}
