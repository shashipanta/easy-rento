package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.PropertyOwnershipRequest;
import com.tms.easyrento.dto.response.OwnerResponse;
import com.tms.easyrento.model.owner.Owner;
import com.tms.easyrento.service.OwnerService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-09 01:17
 */

@Component
public class OwnerMappingHelper {

    private final OwnerService ownerService;

    public OwnerMappingHelper(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    /**
     * This method is used to map the raw id sent as request into the Set<Owner> object
     *
     * @param ids Owner ids
     * @return Set<Owner>
     */
    @SneakyThrows
    public Set<Owner> mapOwnerSet(Set<Long> ids) {
        return ids.stream()
                .map(ownerService::findByUserAccountIdOrGetDetachedOwner)
                .collect(Collectors.toSet());
    }

    public List<Owner> mapOwnerList(List<PropertyOwnershipRequest> propertyOwnershipRequests) {
        return propertyOwnershipRequests.stream()
                .map(PropertyOwnershipRequest::getOwnerId)
                .map(ownerService::findByUserAccountIdOrGetDetachedOwner)
                .toList();
    }

    /**
     * Used to map owners to ownerResponse
     * @param owners Set<Owner>
     * @return Set<OwnerResponse>
     */
    public Set<OwnerResponse> mapOwnerResponse(Set<Owner> owners) {
        return owners.stream()
                .map(OwnerResponse::new)
                .collect(Collectors.toSet());
    }
}

