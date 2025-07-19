package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.PropertyOwnershipRequest;
import com.tms.easyrento.model.owner.Owner;
import com.tms.easyrento.model.propertyOwnership.PropertyOwnership;
import com.tms.easyrento.service.OwnerService;
import com.tms.easyrento.service.PropertyOwnershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-14 14:34
 */

@Component
@RequiredArgsConstructor
public class PropertyOwnershipMappingHelper {

    private final OwnerService ownerService;
    private final PropertyOwnershipService propertyOwnershipService;

    public PropertyOwnership map(PropertyOwnershipRequest request, PropertyOwnership ownership) {

        // for new property ownership requests
        if (ownership == null) {
            ownership = new PropertyOwnership();
        }

        ownership.setOwnershipPercentage(request.getOwnershipPercentage());
        ownership.setStartDate(request.getStartDate());


        if (request.getOwnerId() != null) {
            Owner owner = ownerService.findByUserAccountIdOrGetDetachedOwner(request.getUserAccountId());
            ownership.setOwner(owner);
        }

//        if (request.getPropertyId() != null) {
//            ownership.setProperty(new Property(request.getPropertyId()));
//        }

        return ownership;
    }

    public List<PropertyOwnership> mapList(List<PropertyOwnershipRequest> requests) {
        if (requests == null || requests.isEmpty()) return null;
        Long propertyId = requests.get(0).getPropertyId();
        List<PropertyOwnership> existingOwnerships = propertyOwnershipService.getOwnershipsBy(propertyId);
        List<PropertyOwnership> editedOwnershipList = new ArrayList<>();
        // existing property ownership if edited
        for (PropertyOwnership propertyOwnership : existingOwnerships) {

            for (PropertyOwnershipRequest ownershipRequest : requests) {
                if (ownershipRequest.getId() != null &&
                        ownershipRequest.getId().equals(propertyOwnership.getId())
                        && ownershipRequest.isEdited()) {
                    PropertyOwnership editedOwnership = map(ownershipRequest, propertyOwnership);
                    editedOwnershipList.add(editedOwnership);
                }
            }
        }

        // new property ownership request
        List<PropertyOwnership> newPropertyOwnerships = requests.stream()
                .filter(request -> request.getId() == null)
                .map(propertyOwnershipRequest -> map(propertyOwnershipRequest, null))
                .collect(Collectors.toList());

        newPropertyOwnerships.addAll(editedOwnershipList);

        return newPropertyOwnerships;
    }
}
