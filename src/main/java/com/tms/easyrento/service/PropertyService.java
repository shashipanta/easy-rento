package com.tms.easyrento.service;

import com.tms.easyrento.dto.request.PropertyRequest;
import com.tms.easyrento.dto.request.RentalOfferRequest;
import com.tms.easyrento.dto.response.PropertyMetaDataResponse;
import com.tms.easyrento.dto.response.PropertyResponse;
import com.tms.easyrento.dto.response.SinglePropertyResponse;
import com.tms.easyrento.model.property.Property;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/7/23 9:52 PM
 */
public interface PropertyService extends CurdService<PropertyRequest, Property, PropertyResponse, Long> {

    // Get meta information related to property
    PropertyMetaDataResponse getPropertyMetaInfo();

    Boolean rentalRequest(RentalOfferRequest rentRequest);

    List<PropertyResponse> getBy(Long ownerId);

    List<PropertyResponse> getAll(String isActive);

    PropertyResponse getPropertyInfo(Long propertyId);

    List<PropertyResponse> getPropertyBy();

    List<SinglePropertyResponse> getSinglePropertyInfo(Long propertyId);

}
