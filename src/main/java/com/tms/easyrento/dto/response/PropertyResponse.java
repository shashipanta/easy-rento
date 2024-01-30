package com.tms.easyrento.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/7/23 9:36 PM
 */

@Getter
@Setter
@Builder
public class PropertyResponse {

    private Long id;

    private String propertyCode;

    private String propertyTitle;

    private String propertyType;

    private Long allocatedPrice;

    private Long pricePerUnit;

    private boolean isSystemEvaluatedPrice;

    private Long dynamicPrice;

    private Short totalRooms;

    private boolean occupied;

    private boolean active;

    private String createdOn;

    private OwnerResponse ownerResponse;

    private AddressResponse addressResponse;

    // for images
    private List<String> images;

}
