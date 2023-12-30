package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.AddressRequest;
import com.tms.easyrento.dto.response.AddressResponse;
import com.tms.easyrento.model.Address;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper extends DefaultMapper<AddressRequest, Address, AddressResponse, Long> {
    @Override
    Address requestToEntity(AddressRequest requestDto);

    @Override
    AddressResponse entityToResponse(Address model);
}