package com.tms.easyrento.service.impl;

import com.tms.easyrento.dto.request.DynamicPricingRequest;
import com.tms.easyrento.dto.request.PropertyRequest;
import com.tms.easyrento.dto.request.RentalOfferRequest;
import com.tms.easyrento.dto.response.PropertyMetaDataResponse;
import com.tms.easyrento.dto.response.PropertyResponse;
import com.tms.easyrento.enums.PropertyType;
import com.tms.easyrento.mappers.PropertyMapper;
import com.tms.easyrento.mappers.RentalOfferMapper;
import com.tms.easyrento.model.file.PropertyImage;
import com.tms.easyrento.model.property.Property;
import com.tms.easyrento.repository.PropertyImageRepo;
import com.tms.easyrento.repository.PropertyRepo;
import com.tms.easyrento.repository.RentalOfferRepo;
import com.tms.easyrento.service.DynamicPricingService;
import com.tms.easyrento.service.OwnerService;
import com.tms.easyrento.service.PropertyService;
import com.tms.easyrento.util.file.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/7/23 9:56 PM
 */
@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepo propertyRepo;
    private final PropertyImageRepo propertyImageRepo;
    private final OwnerService ownerService;
    private final DynamicPricingService dynamicPricingService;

    private final PropertyMapper propertyMapper;
    private final RentalOfferMapper rentalOfferMapper;
    private final RentalOfferRepo rentalOfferRepo;

    @Override
    public Long create(PropertyRequest request) {
        Property property = propertyMapper.requestToEntity(request);
        property.setPropertyCode(generateRandomPropertyCode(request.getPropertyType()));
        List<PropertyImage> propertyImages = new ArrayList<>();
        for(MultipartFile file: request.getMultipartFiles()) {
            try {
                FileUtils.FileResponse fileResponse = FileUtils.saveFile(file, "PROPERTY_IMAGE");
                PropertyImage propertyImage = PropertyImage.builder()
                        .fileSize(fileResponse.getFileSize())
                        .fileName(fileResponse.getFileName())
                        .originalFileName(fileResponse.getOriginalFileName())
                        .build();
                // add relation to property
                propertyImage.setProperty(property);
                propertyImages.add(propertyImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // for Land
        setLandPropertyInfo(property);
        property.setPropertyImage(propertyImages);

        // Get Dynamic Price
        DynamicPricingRequest dynamicPricingRequest = toDynamicPricingRequest(request);
        Long dynamicPrice = dynamicPricingService.getDynamicPrice(dynamicPricingRequest);
        property.setDynamicPrice(dynamicPrice);

        return propertyRepo.save(property).getId();
    }

    // TODO: change the default values
    private DynamicPricingRequest toDynamicPricingRequest(PropertyRequest request) {
        return DynamicPricingRequest.builder()
                .total_rooms(String.valueOf(request.getTotalRooms()))
                .total_bedrooms(String.valueOf(request.getTotalBedRooms()))
                .total_living_rooms(String.valueOf(request.getTotalLivingRooms()))
                .hotwater(String.valueOf(1))
                .location("City")
                .electricity("Yes")
                .parking(String.valueOf(1))
                .terrace(String.valueOf(1))
                .markets(String.valueOf(1))
                .build();
    }

    private void setLandPropertyInfo(Property property) {
        String land = PropertyType.LAND.getTypeName();
        if(land.equalsIgnoreCase(property.getPropertyType().getTypeName())) {
            property.setTotalRooms(null);
            property.setTotalBedRooms(null);
            property.setTotalBathRooms(null);
            property.setTotalLivingRooms(null);
        }
    }

    private String generateRandomPropertyCode(String propertyType) {
        return propertyType.toUpperCase() + UUID.randomUUID();
    }

    @Override
    public Long update(PropertyRequest request, Long aLong) {
        Property property = propertyRepo.findById(aLong).orElseThrow();
        return propertyMapper.partialUpdate(request, property).getId();
    }

    @Override
    public List<PropertyResponse> read(String isActive) {
        List<Property> propertyList = propertyRepo.findByActive(isActive);

        List<PropertyResponse> propertyResponses = new ArrayList<>();
        // get property Image
        propertyList.forEach(
                property ->
                    setPropertyImages(property, propertyResponses)
        );

//        return propertyList.stream()
//                .map(propertyMapper::entityToResponse)
//                .toList();

        return propertyResponses;
    }

    private void setPropertyImages(Property property, List<PropertyResponse> propertyResponses) {

        List<PropertyImage> propertyImageById = propertyImageRepo.getPropertyImageById(property.getId());
        List<String> propertyImages = propertyImageById.stream()
                .map(this::mapPropertyImageToString)
                .toList();

        PropertyResponse build = PropertyResponse.builder()
                .id(property.getId())
                .images(propertyImages)
                .propertyTitle(property.getTitle())
                .propertyType(String.valueOf(property.getPropertyType()))
                .active(property.isActive())
                .allocatedPrice(property.getAllocatedPrice())
                .dynamicPrice(property.getDynamicPrice())
                .pricePerUnit(property.getPricePerUnit())
                .occupied(property.isOccupied())
                .createdOn(String.valueOf(property.getCreatedOn()))
                .totalRooms(property.getTotalRooms())
                .build();

        propertyResponses.add(build);
    }

    @Override
    public PropertyResponse read(Long aLong) {
        Property property = propertyRepo.findById(aLong).orElseThrow();
        return propertyMapper.entityToResponse(property);
    }

    @Override
    public void delete(Long aLong) {
        propertyRepo.toggleActiveStatus(aLong);
    }

    @Override
    public boolean hardDelete(Long aLong) {
        return false;
    }

    @Override
    public Property model(Long aLong) {
        return null;
    }

    @Override
    public PropertyMetaDataResponse getPropertyMetaInfo() {
        return PropertyMetaDataResponse.builder()
                .propertyType(PropertyType.getAvailablePropertyTypes())
                .ownerResponses(ownerService.read("1"))
                .build();
    }
    @Override
    public Boolean rentalRequest(RentalOfferRequest rentRequest) {
        //
        rentalOfferRepo.save(rentalOfferMapper.requestToEntity(rentRequest));
        return Boolean.TRUE;
    }

    @Override
    public List<PropertyResponse> getBy(Long ownerId) {
        return propertyRepo.getPropertyByOwnerId(ownerId)
                .stream()
                .map(propertyMapper::entityToResponse)
                .toList();
    }

    protected String mapPropertyImageToString(PropertyImage propertyImage) {
        //TODO Implement mapping
        return propertyImage.getFileName();
    }
}
