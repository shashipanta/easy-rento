package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.PropertyRequest;
import com.tms.easyrento.dto.response.PropertyResponse;
import com.tms.easyrento.model.property.Property;
import com.tms.easyrento.service.PropertyService;
import org.mapstruct.*;


@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {OwnerMappingHelper.class, PropertyService.class}
)
public interface PropertyMapper extends DefaultMapper<PropertyRequest, Property, PropertyResponse, Long> {

    @Mapping(source = "ownerIds", target = "owners")
    @Mapping(source = "wardNo", target = "address.wardNo")
    @Mapping(source = "googleLocation", target = "address.googleLocation")
    @Mapping(source = "streetName", target = "address.streetName")
    @Mapping(source = "addressType", target = "address.addressType")
    @Mapping(source = "propertyTitle", target = "title")
    Property requestToEntity(PropertyRequest propertyRequest);

    PropertyRequest toDto(Property property);

    @Mapping(source = "active", target = "active")
    @Mapping(source = "createdOn", target = "createdOn", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(source = "owners", target = "ownerResponse")
    @Mapping(source = "title", target = "propertyTitle")
    @Mapping(source = "systemEvaluatedPrice", target = "isSystemEvaluatedPrice")
    PropertyResponse entityToResponse(Property property);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "ownerIds", target = "owners")
    Property partialUpdate(PropertyRequest propertyRequest, @MappingTarget Property property);
}