package com.tms.easyrento.service;

import com.tms.easyrento.dto.request.OwnerRequest;
import com.tms.easyrento.dto.response.OwnerResponse;
import com.tms.easyrento.dto.response.RentalOfferResponse;
import com.tms.easyrento.dto.response.TenantResponse;
import com.tms.easyrento.model.owner.Owner;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/6/23 8:43 PM
 */
public interface OwnerService extends CurdService<OwnerRequest, Owner, OwnerResponse, Long> {

    Long create(OwnerRequest request);

    Long update(OwnerRequest request, Long aLong);

    List<OwnerResponse> read(String isActive) ;

    void delete(Long aLong) ;

    boolean hardDelete(Long aLong) ;

    Owner model(Long aLong) ;

    Boolean terminateContract(Long contractId);

    List<TenantResponse> associatedTenants();

    List<RentalOfferResponse> rentalOffers(Long ownerId);

    Integer rentOfferCounts(Long ownerId);

}
