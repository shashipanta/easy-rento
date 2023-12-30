package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.ContractRequest;
import com.tms.easyrento.dto.response.ContractResponse;
import com.tms.easyrento.model.contract.Contract;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContractMapper extends
        DefaultMapper<ContractRequest, Contract, ContractResponse, Long> {

    @Override
    Contract requestToEntity(ContractRequest requestDto);

    @Override
    ContractResponse entityToResponse(Contract model);
}