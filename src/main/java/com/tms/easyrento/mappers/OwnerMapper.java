package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.OwnerRequest;
import com.tms.easyrento.dto.response.OwnerResponse;
import com.tms.easyrento.model.owner.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnerMapper extends DefaultMapper<OwnerRequest, Owner, OwnerResponse, Long> {

    @Override
    OwnerResponse entityToResponse(Owner model);
}