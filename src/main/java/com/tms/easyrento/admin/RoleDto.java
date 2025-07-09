package com.tms.easyrento.admin;

import com.tms.easyrento.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-04 17:08
 */

@Getter
@Setter
@NoArgsConstructor
public class RoleDto {

    private Long id;

    private String name;

    private String description;

    private boolean active;

    private Status status;

    public RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
