package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.PropertyOwnershipRequest;
import com.tms.easyrento.dto.request.PropertyRequest;
import com.tms.easyrento.dto.request.RoomRequest;
import com.tms.easyrento.dto.response.PropertyResponse;
import com.tms.easyrento.enums.AddressType;
import com.tms.easyrento.enums.RoomType;
import com.tms.easyrento.model.Address;
import com.tms.easyrento.model.property.Property;
import com.tms.easyrento.model.property.Room;
import com.tms.easyrento.model.propertyOwnership.PropertyOwnership;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {OwnerMappingHelper.class, PropertyOwnershipMappingHelper.class}
)
public interface PropertyMapper extends DefaultMapper<PropertyRequest, Property, PropertyResponse, Long> {

    @Mapping(source = "wardNo", target = "address.wardNo")
    @Mapping(source = "googleLocation", target = "address.googleLocation")
    @Mapping(source = "streetName", target = "address.streetName")
    @Mapping(source = "addressType", target = "address.addressType")
    @Mapping(source = "propertyTitle", target = "title")
    @Mapping(source = "propertyOwnershipRequests", target = "propertyOwnerships")
    @Mapping(source = "rooms", target = "rooms", qualifiedByName = "mapListToSet")
    Property requestToEntity(PropertyRequest propertyRequest);

    PropertyRequest toDto(Property property);

    @Mapping(source = "active", target = "active")
    @Mapping(source = "createdOn", target = "createdOn", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(source = "title", target = "propertyTitle")
    @Mapping(source = "systemEvaluatedPrice", target = "isSystemEvaluatedPrice")
    PropertyResponse entityToResponse(Property property);

    @Mapping(source = "propertyTitle", target = "title")
    @Mapping(source = "propertyOwnershipRequests", target = "propertyOwnerships")
    @Mapping(source = "rooms", target = "rooms", qualifiedByName = "mapListToSet")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Property partialUpdate(PropertyRequest propertyRequest, @MappingTarget Property property);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PropertyOwnership toPropertyOwnership(PropertyOwnershipRequest propertyOwnershipRequest,
                                          @MappingTarget PropertyOwnership propertyOwnership);

    // property is to be saved first so property is not mapped here
    @Named(value = "mapListToSet")
    default Set<Room> mapListToSet(List<RoomRequest> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            return null;
        }

        Set<Room> set = new HashSet<>();
        for (RoomRequest roomRequest : rooms) {
            Room room = Room.builder()
                    .id(roomRequest.getId())
                    .roomType(RoomType.valueOf(roomRequest.getRoomName()))
                    .totalRoom(roomRequest.getTotalRooms())
                    .build();
            set.add(room);
        }

        return set;
    }

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