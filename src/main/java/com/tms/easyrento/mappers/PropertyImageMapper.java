package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.PropertyImageRequest;
import com.tms.easyrento.model.file.PropertyImage;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PropertyImageMapper {
    PropertyImage toEntity(PropertyImageRequest propertyImageRequest);

    default List<MultipartFile> toMultipartFile(List<PropertyImageRequest> propertyImageRequests) {
        List<MultipartFile> multipartFiles = new ArrayList<>();
        for(PropertyImageRequest request: propertyImageRequests) {
            multipartFiles.add(request.getFile());
        }
        return multipartFiles;
    }

    PropertyImageRequest toDto(PropertyImage propertyImage);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PropertyImage partialUpdate(PropertyImageRequest propertyImageRequest, @MappingTarget PropertyImage propertyImage);
}