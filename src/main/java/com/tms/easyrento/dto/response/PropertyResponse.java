package com.tms.easyrento.dto.response;

import lombok.*;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/7/23 9:36 PM
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyResponse {

    private Long id;

    private String propertyCode;

    private String propertyTitle;

    private String propertyType;

    private Long allocatedPrice;

    private Long pricePerUnit;

    private Boolean isSystemEvaluatedPrice;

    private Long dynamicPrice;

    private Short totalRooms;

    private Boolean occupied;

    private Boolean active;

    private String createdOn;

    private OwnerResponse ownerResponse;

    private AddressResponse addressResponse;

    // for images
    private List<String> images;

    private List<PropertyImageResponse> propertyImages;

    private String description;

    @Getter
    @Setter
    public static class PropertyImageResponse {
        private Long id;
        private List<String> images;
    }

}
