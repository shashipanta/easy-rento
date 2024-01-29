package com.tms.easyrento.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.easyrento.dto.request.RentalOfferRequest;
import com.tms.easyrento.dto.response.RentalOfferResponse;
import com.tms.easyrento.mappers.RentalOfferMapper;
import com.tms.easyrento.model.rent.RentalOffer;
import com.tms.easyrento.model.rent.RentalOfferProjection;
import com.tms.easyrento.repository.RentalOfferRepo;
import com.tms.easyrento.service.RentalOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/28/24 10:55 AM
 */

@Service
@RequiredArgsConstructor
public class RentalOfferServiceImpl implements RentalOfferService {

    private final RentalOfferRepo rentalOfferRepo;
    private final RentalOfferMapper rentalOfferMapper;
    private final ObjectMapper objectMapper;

    @Override
    public Long create(RentalOfferRequest request) {
        return null;
    }

    @Override
    public Long update(RentalOfferRequest request, Long aLong) {
        return null;
    }

    @Override
    public List<RentalOfferResponse> read(String isActive) {
        return null;
    }

    @Override
    public RentalOfferResponse read(Long aLong) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public boolean hardDelete(Long aLong) {
        return false;
    }

    @Override
    public RentalOffer model(Long aLong) {
        return null;
    }

    @Override
    public List<RentalOfferResponse> getOffers(Long id) {
        List<RentalOfferProjection> offers = rentalOfferRepo.offers(id);
        return offers.stream()
                .map(this::toRentalOfferResponse)
                .toList();
    }

    @Override
    public Long totalOffersBy(Long ownerId) {
        return rentalOfferRepo.rentalOfferCount(ownerId);
    }

    private RentalOfferResponse toRentalOfferResponse(RentalOfferProjection rentalOfferProjection) {
        return objectMapper.convertValue(rentalOfferProjection, RentalOfferResponse.class);
    }
}
