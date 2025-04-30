package com.tms.easyrento.dto.request;

import com.tms.easyrento.constants.FieldErrorConstants;
import com.tms.easyrento.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/5/23 11:34 PM
 */

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    private String username;

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    private String password;

    @Email(message = "email format not valid")
    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    private String email;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private UserType userType = UserType.TENANT;
}
