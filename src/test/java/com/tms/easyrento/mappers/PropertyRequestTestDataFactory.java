package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.PropertyRequest;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-15 13:05
 */

public class PropertyRequestTestDataFactory {

    public static PropertyRequest withAddressFields() {
        PropertyRequest propertyRequest = new PropertyRequest();
        propertyRequest.setWardNo((short) 5);
        propertyRequest.setStreetName("Main Street");
        propertyRequest.setStreetNameNp("मुख्य सडक");
        propertyRequest.setGoogleLocation("https://maps.google.com/maps?");
        propertyRequest.setAddressType("P");
        propertyRequest.setPropertyTitle("Luxury Apartment");
        return propertyRequest;
    }

    public static PropertyRequest partialAddressUpdate() {
        PropertyRequest propertyRequest = new PropertyRequest();
        propertyRequest.setWardNo((short) 10);
        propertyRequest.setStreetName("Updated Main Street");
        propertyRequest.setPropertyTitle("Updated Title");
        return propertyRequest;
    }
}
