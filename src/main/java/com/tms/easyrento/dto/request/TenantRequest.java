package com.tms.easyrento.dto.request;

import com.tms.easyrento.constants.FieldErrorConstants;
import com.tms.easyrento.enums.Gender;
import com.tms.easyrento.util.annotations.NepaliPhoneNumber;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/12/23 12:30 AM
 */

@Getter
@Setter
public class TenantRequest {

    private Long id;

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    @Email(message = FieldErrorConstants.INVALID_EMAIL_FORMAT)
    private String email;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private Gender gender;

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    private String name;

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    private String nameNp;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    @Min(value = 5000, message = FieldErrorConstants.NET_WORTH_NOT_SUFFICIENT)
    private Long netWorth;

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    private String occupation;

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    @NepaliPhoneNumber(message = FieldErrorConstants.INVALID_NEPALI_PHONE_NUMBER)
    private String phoneNo;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private AddressRequest address;

}
