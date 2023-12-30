package com.tms.easyrento.service;

import com.tms.easyrento.dto.request.PropertyRequest;
import com.tms.easyrento.dto.response.PropertyMetaDataResponse;
import com.tms.easyrento.dto.response.PropertyResponse;
import com.tms.easyrento.model.property.Property;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/7/23 9:52 PM
 */
public interface PropertyService extends CurdService<PropertyRequest, Property, PropertyResponse, Long> {

    // Get meta information related to property
    PropertyMetaDataResponse getPropertyMetaInfo();

}
