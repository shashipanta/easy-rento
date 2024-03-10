package com.tms.easyrento.model.rent;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/29/24 9:22 PM
 */
public interface RentalOfferProjection {

    Long getTenantId();
    String getTenantName();
    String getTenantGender();
    String getTenantEmail();
    Long getTenantAddressId();

    // owner
    Long getOwnerId();
    String getOwnerName();
    Long getOwnerAddressId();

    // rentalOffer
    Long getRentalOfferId();
    String getRentalOfferStatus();
    boolean getRentalOfferAccepted();
    Long getRentalOfferRequestedPrice();

    // propertyInfo
    Long getPropertyId();
    String getPropertyTitle();
    Long getPropertyAllocatedPrice();
    Long getPropertyDynamicPrice();

}
