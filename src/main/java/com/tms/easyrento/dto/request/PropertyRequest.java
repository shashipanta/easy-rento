package com.tms.easyrento.dto.request;

import com.tms.easyrento.constants.FieldErrorConstants;
import com.tms.easyrento.enums.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/7/23 9:36 PM
 */
@Getter
@Setter
public class PropertyRequest {
    private Long id;

    private String propertyCode;

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    private String propertyTitle;

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    private String propertyType;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private Long allocatedPrice;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private Long pricePerUnit;

    private Short totalRooms;

    private Short totalBathRooms = 1;

    private Short totalBedRooms = 1;

    private Short totalLivingRooms = 1;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private Long ownerId;

    // address field
    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private Short wardNo;

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    private String addressType;

    @NotBlank(message = FieldErrorConstants.NOT_BLANK)
    private String streetName;

    private String streetNameNp;

    private String googleLocation;

    private String description;

    private boolean occupied = false;


//    private List<PropertyImageRequest> imageFiles;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private List<MultipartFile> multipartFiles;

}
