package com.tms.easyrento.service.impl;

import com.tms.easyrento.dto.request.PropertyRequest;
import com.tms.easyrento.dto.response.PropertyMetaDataResponse;
import com.tms.easyrento.dto.response.PropertyResponse;
import com.tms.easyrento.enums.PropertyType;
import com.tms.easyrento.mappers.PropertyMapper;
import com.tms.easyrento.model.file.PropertyImage;
import com.tms.easyrento.model.property.Property;
import com.tms.easyrento.repository.PropertyRepo;
import com.tms.easyrento.service.OwnerService;
import com.tms.easyrento.service.PropertyService;
import com.tms.easyrento.util.file.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    private final OwnerService ownerService;

    private final PropertyMapper propertyMapper;


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
        property.setPropertyImage(propertyImages);
        return propertyRepo.save(property).getId();
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

        return propertyList.stream()
                .map(propertyMapper::entityToResponse)
                .toList();
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
}
