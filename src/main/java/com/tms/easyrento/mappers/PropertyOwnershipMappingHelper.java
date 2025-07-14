package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.PropertyOwnershipRequest;
import com.tms.easyrento.model.owner.Owner;
import com.tms.easyrento.model.property.Property;
import com.tms.easyrento.model.propertyOwnership.PropertyOwnership;
import com.tms.easyrento.model.propertyOwnership.PropertyOwnershipId;
import com.tms.easyrento.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-14 14:34
 */

@Component
@RequiredArgsConstructor
public class PropertyOwnershipMappingHelper {

    private final OwnerService ownerService;

    public PropertyOwnership map(PropertyOwnershipRequest request) {
        PropertyOwnership ownership = new PropertyOwnership();
        ownership.setOwnershipPercentage(request.getOwnershipPercentage());
        ownership.setStartDate(request.getStartDate());

        // Manually set IDs if needed
        PropertyOwnershipId id = new PropertyOwnershipId(request.getOwnerId(), request.getPropertyId());
        ownership.setId(id);

        if (request.getOwnerId() != null) {
            Owner owner = ownerService.findByUserAccountIdOrGetDetachedOwner(request.getUserAccountId());
            ownership.setOwner(owner);
        }

        if (request.getPropertyId() != null) {
            ownership.setProperty(new Property(request.getPropertyId()));
        }

        return ownership;
    }

    public List<PropertyOwnership> mapList(List<PropertyOwnershipRequest> requests) {
        if (requests == null) return new ArrayList<>();
        return requests.stream()
                .map(this::map)
                .toList();
    }
}
