package com.tms.easyrento.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/28/24 10:53 AM
 */
@Getter
@Setter
public class RentalOfferResponse {

    private Long tenantId;
    private String tenantName;
    private String tenantGender;
    private String tenantEmail;
    private Long tenantAddressId;

    private Long ownerId;
    private String ownerName;
    private Long ownerAddressId;

    private Long rentalOfferId;
    private String rentalOfferStatus;
    private boolean rentalOfferAccepted;
    private Long rentalOfferRequestedPrice;

    private Long propertyId;
    private String propertyTitle;
    private Long propertyAllocatedPrice;
    private Long propertyDynamicPrice;
}
