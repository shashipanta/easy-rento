package com.tms.easyrento.dto.request;

import com.tms.easyrento.constants.FieldErrorConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/28/24 10:39 AM
 */

@Getter
@Setter
public class RentalOfferRequest {

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private Long tenantId;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private Long ownerId;

    private String remarks;

    private Long negotiatedPrice;
}
