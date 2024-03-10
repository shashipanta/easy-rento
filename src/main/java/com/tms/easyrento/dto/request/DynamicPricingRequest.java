package com.tms.easyrento.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/30/24 4:24 AM
 */

@Getter
@Setter
@Builder
public class DynamicPricingRequest {

    private String total_rooms;
    private String total_bedrooms;
    private String total_living_rooms;
    private String location;
    private String parking;
    private String hotwater;
    private String electricity;
    private String terrace;
    private String markets;
}
