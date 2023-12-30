package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.TenantRequest;
import com.tms.easyrento.dto.response.TenantResponse;
import com.tms.easyrento.model.tenant.Tenant;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = AddressMapper.class
)
public interface TenantMapper extends DefaultMapper<TenantRequest, Tenant, TenantResponse, Long> {


    @Override
    TenantResponse entityToResponse(Tenant model);
}