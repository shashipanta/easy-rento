package com.tms.easyrento.dto.request;

import com.tms.easyrento.constants.FieldErrorConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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


}
