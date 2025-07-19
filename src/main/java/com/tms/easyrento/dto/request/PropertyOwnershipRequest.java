package com.tms.easyrento.dto.request;

import com.tms.easyrento.constants.FieldErrorConstants;
import com.tms.easyrento.dto.common.DTOGroups;
import com.tms.easyrento.model.propertyOwnership.PropertyOwnershipId;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-11 16:49
 */

@Getter
@Setter
@NoArgsConstructor
public class PropertyOwnershipRequest {

    // this is just for response object
    private PropertyOwnershipId id;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private Long userAccountId;

    private String ownerName;

    private String ownerNameNp;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private Long ownerId;

    private Long propertyId;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private Double ownershipPercentage = 50.0;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private LocalDate startDate = LocalDate.now();

    @NotNull(message = FieldErrorConstants.NOT_NULL, groups = {DTOGroups.UpdateGroup.class})
    private boolean edited = false;

    public PropertyOwnershipRequest(Long ownerId) {
        setOwnerId(ownerId);
    }
}
