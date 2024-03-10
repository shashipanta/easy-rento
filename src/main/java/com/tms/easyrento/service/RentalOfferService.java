package com.tms.easyrento.service;

import com.tms.easyrento.dto.request.RentalOfferRequest;
import com.tms.easyrento.dto.response.RentalOfferResponse;
import com.tms.easyrento.model.rent.RentalOffer;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/28/24 10:48 AM
 */
public interface RentalOfferService extends CurdService<RentalOfferRequest, RentalOffer, RentalOfferResponse, Long>{

    List<RentalOfferResponse> getOffers(Long id);

    Long totalOffersBy(Long ownerId);
}
