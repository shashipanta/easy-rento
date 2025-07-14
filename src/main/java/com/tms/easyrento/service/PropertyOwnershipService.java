package com.tms.easyrento.service;

import com.tms.easyrento.dto.request.PropertyOwnershipRequest;
import com.tms.easyrento.dto.response.PropertyOwnershipResponse;
import com.tms.easyrento.model.propertyOwnership.PropertyOwnership;
import com.tms.easyrento.model.propertyOwnership.PropertyOwnershipId;

import java.util.List;

public interface PropertyOwnershipService extends CurdService<PropertyOwnershipRequest,
        PropertyOwnershipService,
        PropertyOwnershipResponse,
        PropertyOwnershipId> {

    void assignOwnership(PropertyOwnershipRequest propertyOwnershipRequest);

    void assignOwnership(List<PropertyOwnership> propertyOwnerships);

    void removeOwnership(PropertyOwnershipRequest propertyOwnershipRequest);

    void assignMultipleOwnership(List<PropertyOwnershipRequest> propertyOwnershipRequests);

}
