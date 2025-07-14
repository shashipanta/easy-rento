package com.tms.easyrento.service.impl;

import com.tms.easyrento.config.security.util.JwtUtils;
import com.tms.easyrento.dto.request.DynamicPricingRequest;
import com.tms.easyrento.dto.request.PropertyRequest;
import com.tms.easyrento.dto.request.RentalOfferRequest;
import com.tms.easyrento.dto.response.PropertyMetaDataResponse;
import com.tms.easyrento.dto.response.PropertyResponse;
import com.tms.easyrento.dto.response.SinglePropertyResponse;
import com.tms.easyrento.enums.PropertyType;
import com.tms.easyrento.mappers.PropertyMapper;
import com.tms.easyrento.mappers.RentalOfferMapper;
import com.tms.easyrento.model.file.PropertyImage;
import com.tms.easyrento.model.property.Property;
import com.tms.easyrento.model.propertyOwnership.PropertyOwnership;
import com.tms.easyrento.repository.PropertyImageRepo;
import com.tms.easyrento.repository.PropertyRepo;
import com.tms.easyrento.repository.RentalOfferRepo;
import com.tms.easyrento.service.DynamicPricingService;
import com.tms.easyrento.service.OwnerService;
import com.tms.easyrento.service.PropertyOwnershipService;
import com.tms.easyrento.service.PropertyService;
import com.tms.easyrento.util.file.FileUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
    private final com.tms.easyrento.dbMappers.PropertyMapper iPropertyMapper;
    private final RentalOfferMapper rentalOfferMapper;
    private final RentalOfferRepo rentalOfferRepo;

    private final JwtUtils jwtUtils;
    private final PropertyOwnershipService propertyOwnershipService;

    @Override
    @Transactional
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
        List<PropertyOwnership> propertyOwnerships = property.getPropertyOwnerships();
        property.setPropertyOwnerships(Collections.emptyList());
        Property savedProperty =  propertyRepo.save(property);


        // assign newly created propertyId to the requests
        propertyOwnerships.forEach(propertyOwnership -> {
            propertyOwnership.setProperty(savedProperty);
        });

        propertyOwnershipService.assignOwnership(propertyOwnerships);

        return savedProperty.getId();

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
        return UUID.randomUUID().toString();
    }

    @Override
    public Long update(PropertyRequest request, Long aLong) {
        Property property = propertyRepo.findById(aLong).orElseThrow();
        return propertyRepo.save(propertyMapper.partialUpdate(request, property)).getId();
    }

    @Override
    public List<PropertyResponse> read(String isActive) {
        List<PropertyResponse> activeList = iPropertyMapper.findActiveList(isActive);
        return activeList;
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
                .stream().map(this::toPropertyResponse)
                .toList();
    }

    public PropertyResponse toPropertyResponse(Property property){
        return PropertyResponse.builder()
                .dynamicPrice(property.getDynamicPrice())
                .propertyTitle(property.getTitle())
                .id(property.getId())
                .allocatedPrice(property.getAllocatedPrice())
                .propertyType(property.getPropertyType().name())
                .occupied(property.isOccupied())
                .createdOn(String.valueOf(property.getCreatedOn()))
                .pricePerUnit(property.getPricePerUnit())
                .build();
    }

    @Override
    public List<PropertyResponse> getAll(String isActive) {
        List<Property> propertyList = propertyRepo.findByActive(isActive);
        return propertyList.stream()
                .map(propertyMapper::entityToResponse)
                .toList();
    }

    @Override
    public PropertyResponse getPropertyInfo(Long propertyId) {
        // todo: add string constants for this "userId"
        Long ownerId = jwtUtils.getLoggedUserId();
        PropertyResponse singlePropertyByOwnerId = iPropertyMapper.findSinglePropertyByOwnerId(ownerId, propertyId);
        return singlePropertyByOwnerId;

    }

    @Override
    public List<PropertyResponse> getPropertyBy() {
        Long ownerId = jwtUtils.getLoggedUserId();
        return iPropertyMapper.findPropertiesByOwnerId(ownerId);
    }

    @Override
    public List<SinglePropertyResponse> getSinglePropertyInfo(Long propertyId) {
        return iPropertyMapper.getSinglePropertyForAdmin(propertyId);
    }

}
