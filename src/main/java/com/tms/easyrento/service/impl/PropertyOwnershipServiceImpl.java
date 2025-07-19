package com.tms.easyrento.service.impl;

import com.tms.easyrento.dto.request.PropertyOwnershipRequest;
import com.tms.easyrento.dto.response.PropertyOwnershipResponse;
import com.tms.easyrento.model.owner.Owner;
import com.tms.easyrento.model.property.Property;
import com.tms.easyrento.model.propertyOwnership.PropertyOwnership;
import com.tms.easyrento.model.propertyOwnership.PropertyOwnershipId;
import com.tms.easyrento.repository.PropertyOwnershipRepo;
import com.tms.easyrento.service.PropertyOwnershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-11 19:42
 */

@Service
@RequiredArgsConstructor
public class PropertyOwnershipServiceImpl implements PropertyOwnershipService {

    private final PropertyOwnershipRepo propertyOwnershipRepo;

    @Override
    public PropertyOwnershipId create(PropertyOwnershipRequest request) {
        return null;
    }

    @Override
    public PropertyOwnershipId update(PropertyOwnershipRequest request, PropertyOwnershipId propertyOwnershipId) {
        return null;
    }

    @Override
    public List<PropertyOwnershipResponse> read(String isActive) {
        return List.of();
    }

    @Override
    public PropertyOwnershipResponse read(PropertyOwnershipId propertyOwnershipId) {
        return null;
    }

    @Override
    public void delete(PropertyOwnershipId propertyOwnershipId) {

    }

    @Override
    public boolean hardDelete(PropertyOwnershipId propertyOwnershipId) {
        return false;
    }

    @Override
    public PropertyOwnershipService model(PropertyOwnershipId propertyOwnershipId) {
        return null;
    }

    @Override
    public void assignOwnership(PropertyOwnershipRequest propertyOwnershipRequest) {
        PropertyOwnershipId propertyOwnershipId = new PropertyOwnershipId(
                propertyOwnershipRequest.getOwnerId(),
                propertyOwnershipRequest.getPropertyId()
        );

        PropertyOwnership propertyOwnership = new PropertyOwnership();
//        propertyOwnership.setId(propertyOwnershipId);
        propertyOwnership.setProperty(new Property(propertyOwnershipRequest.getPropertyId()));
        propertyOwnership.setOwnershipPercentage(propertyOwnershipRequest.getOwnershipPercentage());
        propertyOwnership.setStartDate(propertyOwnershipRequest.getStartDate());
        propertyOwnership.setOwner(new Owner(propertyOwnershipRequest.getOwnerId()));

        propertyOwnershipRepo.save(propertyOwnership);
    }

    @Override
    public void assignOwnership(List<PropertyOwnership> propertyOwnerships) {

        propertyOwnershipRepo.saveAll(propertyOwnerships);
    }

    @Override
    public void removeOwnership(PropertyOwnershipRequest propertyOwnershipRequest) {

    }

    @Override
    public void assignMultipleOwnership(List<PropertyOwnershipRequest> propertyOwnershipRequests) {

    }

    @Override
    public List<PropertyOwnership> getOwnershipsBy(Long propertyId) {
        List<PropertyOwnership> ownershipsByProperty = propertyOwnershipRepo.findById_PropertyId(propertyId);
        return ownershipsByProperty;
    }
}
