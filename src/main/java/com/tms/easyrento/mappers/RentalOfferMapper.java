package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.RentalOfferRequest;
import com.tms.easyrento.dto.response.PropertyResponse;
import com.tms.easyrento.dto.response.RentalOfferResponse;
import com.tms.easyrento.model.property.Property;
import com.tms.easyrento.model.rent.RentalOffer;
import com.tms.easyrento.service.OwnerService;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/28/24 11:01 AM
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface RentalOfferMapper extends DefaultMapper<RentalOfferRequest, RentalOffer, RentalOfferResponse, Long> {

    RentalOfferResponse entityToResponse(RentalOffer rentalOffer);
}
