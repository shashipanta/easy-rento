package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.PropertyImageRequest;
import com.tms.easyrento.dto.request.PropertyRequest;
import com.tms.easyrento.dto.response.PropertyResponse;
import com.tms.easyrento.model.owner.Owner;
import com.tms.easyrento.model.property.Property;
import com.tms.easyrento.repository.PropertyRepo;
import com.tms.easyrento.service.OwnerService;
import com.tms.easyrento.service.PropertyService;
import com.tms.easyrento.service.impl.PropertyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {OwnerService.class, OwnerMapper.class, PropertyImageMapper.class})
public interface PropertyMapper extends DefaultMapper<PropertyRequest, Property, PropertyResponse, Long> {


    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "wardNo", target = "address.wardNo")
    @Mapping(source = "googleLocation", target = "address.googleLocation")
    @Mapping(source = "streetName", target = "address.streetName")
    @Mapping(source = "addressType", target = "address.addressType")
    @Mapping(source = "propertyTitle", target = "title")
//    @Mapping(target = "imageUrl", ignore = true)
    Property requestToEntity(PropertyRequest propertyRequest);

    default Owner map(Long value, @Context OwnerService ownerService) {
        return ownerService.model(value);
    }

    PropertyRequest toDto(Property property);

    @Mapping(source = "active", target = "active")
    @Mapping(source = "createdOn", target = "createdOn", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(source = "owner", target = "ownerResponse")
    @Mapping(source = "title", target = "propertyTitle")
    PropertyResponse entityToResponse(Property property);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Property partialUpdate(PropertyRequest propertyRequest, @MappingTarget Property property);
}