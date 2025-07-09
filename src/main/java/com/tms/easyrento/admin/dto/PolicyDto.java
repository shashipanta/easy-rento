package com.tms.easyrento.admin.dto;

import com.tms.easyrento.admin.Policy;
import com.tms.easyrento.admin.RoleDto;
import com.tms.easyrento.constants.FieldErrorConstants;
import com.tms.easyrento.model.auth.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-05 00:21
 */

@Getter
@Setter
public class PolicyDto {

    private Long id;

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    private String name;

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    private String controllerName;

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    private String httpMethod;

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    private String resourcePattern;

    private String description;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    @NotEmpty(message = FieldErrorConstants.NOT_EMPTY)
    private List<RoleDto> roles;

    public Policy toModel(PolicyDto policyDto) {
        return Policy.builder()
                .id(policyDto.getId())
                .name(policyDto.getName())
                .controllerName(policyDto.getControllerName())
                .httpMethod(policyDto.getHttpMethod())
                .resourcePattern(policyDto.getResourcePattern())
                .description(policyDto.getDescription())
                .roles(policyDto.getRoles()
                        .stream().map(roleDto -> new Role(roleDto.getId()))
                        .collect(Collectors.toSet())
                )
                .build();
    }

    public PolicyDto toDto(Policy policy) {
        PolicyDto policyDto = new PolicyDto();

        policyDto.setId(policy.getId());
        policyDto.setName(policy.getName());
        policyDto.setControllerName(policy.getControllerName());
        policyDto.setHttpMethod(policy.getHttpMethod());
        policyDto.setResourcePattern(policy.getResourcePattern());
        policyDto.setDescription(policy.getDescription());
        policyDto.setRoles(policy.getRoles().stream()
                .map(role -> new RoleDto(role.getId(),role.getName()))
                .collect(Collectors.toList())
        );

        return policyDto;
    }

}
