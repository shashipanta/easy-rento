package com.tms.easyrento.dto.response;

import com.tms.easyrento.model.propertyOwnership.PropertyOwnershipId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-11 16:53
 */

@Getter
@Setter
public class PropertyOwnershipResponse extends AbstractAuditorResponse {

    private PropertyOwnershipId id;

    private OwnerResponse owner;

    private PropertyResponse property;

    private Double ownershipPercentage = 50.0;

    private LocalDate startDate = LocalDate.now();

}
