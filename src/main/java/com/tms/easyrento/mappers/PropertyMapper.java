package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.PropertyRequest;
import com.tms.easyrento.dto.response.PropertyResponse;
import com.tms.easyrento.enums.AddressType;
import com.tms.easyrento.model.Address;
import com.tms.easyrento.model.property.Property;
import com.tms.easyrento.service.PropertyService;
import org.mapstruct.*;


@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {OwnerMappingHelper.class, PropertyService.class, PropertyOwnershipMappingHelper.class}
)
public interface PropertyMapper extends DefaultMapper<PropertyRequest, Property, PropertyResponse, Long> {

    @Mapping(source = "wardNo", target = "address.wardNo")
    @Mapping(source = "googleLocation", target = "address.googleLocation")
    @Mapping(source = "streetName", target = "address.streetName")
    @Mapping(source = "addressType", target = "address.addressType")
    @Mapping(source = "propertyTitle", target = "title")
    @Mapping(source = "propertyOwnershipRequests", target = "propertyOwnerships")
    Property requestToEntity(PropertyRequest propertyRequest);

    PropertyRequest toDto(Property property);

    @Mapping(source = "active", target = "active")
    @Mapping(source = "createdOn", target = "createdOn", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(source = "title", target = "propertyTitle")
    @Mapping(source = "systemEvaluatedPrice", target = "isSystemEvaluatedPrice")
    PropertyResponse entityToResponse(Property property);

    @Mapping(source = "propertyTitle", target = "title")
    @Mapping(source = "propertyOwnershipRequests", target = "propertyOwnerships")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Property partialUpdate(PropertyRequest propertyRequest, @MappingTarget Property property);

    @AfterMapping
    default void mergeAddress(PropertyRequest request, @MappingTarget Property property) {
        if (request == null) return;

        Address address = property.getAddress();
        if (address == null) {
            address = new Address();
            property.setAddress(address);
        }

        if (request.getAddressId() != null) {
            address.setId(request.getAddressId());
        }

        if (request.getWardNo() != null) {
            address.setWardNo(request.getWardNo().longValue());
        }

        if (request.getStreetName() != null) {
            address.setStreetName(request.getStreetName());
        }

        if (request.getStreetNameNp() != null) {
            address.setStreetNameNp(request.getStreetNameNp());
        }

        if (request.getGoogleLocation() != null) {
            address.setGoogleLocation(request.getGoogleLocation());
        }

        if (request.getAddressType() != null) {
            address.setAddressType(AddressType.valueOf(request.getAddressType()));
        }
    }


}