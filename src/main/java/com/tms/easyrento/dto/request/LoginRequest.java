package com.tms.easyrento.dto.request;

import com.tms.easyrento.constants.FieldErrorConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/10/24 11:50 PM
 */

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    private String username;

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    private String password;
}
