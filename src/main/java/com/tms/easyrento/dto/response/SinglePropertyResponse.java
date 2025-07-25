package com.tms.easyrento.dto.response;

import com.tms.easyrento.enums.RoomType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 3/1/24 2:52 PM
 */
@Getter
@Setter
public class SinglePropertyResponse {

    private Long id;

    private String propertyCode;

    private String propertyTitle;

    private String propertyType;

    private Long allocatedPrice;

    private Long pricePerUnit;

    private Boolean isSystemEvaluatedPrice;

    private Long dynamicPrice;

    private Short totalRooms;

    private List<RoomInfo> rooms;

    private Boolean occupied;

    private Boolean active;

    private String createdOn;

    private ImageResponse featuredImage;

    private List<ImageResponse> secondaryImages;

    private List<PropertyOwnershipResponse> propertyOwnerships;

    @Getter
    @Setter
    public static final class RoomInfo {
        private RoomType roomType;
        private Short noOfRooms;
    }

}
