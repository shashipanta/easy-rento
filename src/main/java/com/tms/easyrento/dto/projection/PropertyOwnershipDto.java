package com.tms.easyrento.dto.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-11 17:40
 */

@Getter
@Setter
@AllArgsConstructor
public class PropertyOwnershipDto {
    private Long ownerId;
    private Long propertyId;
    private Double ownershipPercentage;
    private LocalDate startDate;
}
