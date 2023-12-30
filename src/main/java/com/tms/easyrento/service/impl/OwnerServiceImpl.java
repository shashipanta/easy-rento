package com.tms.easyrento.service.impl;

import com.tms.easyrento.dto.request.OwnerRequest;
import com.tms.easyrento.dto.response.OwnerResponse;
import com.tms.easyrento.mappers.OwnerMapper;
import com.tms.easyrento.model.owner.Owner;
import com.tms.easyrento.repository.ContractRepo;
import com.tms.easyrento.repository.OwnerRepo;
import com.tms.easyrento.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/8/23 6:04 AM
 */
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepo ownerRepo;

    private final OwnerMapper ownerMapper;
    private final ContractRepo contractRepo;

    @Override
    public OwnerResponse read(Long aLong) {
        Owner owner = ownerRepo.findById(aLong).orElseThrow();
        return ownerMapper.entityToResponse(owner);
    }

    @Override
    public Long create(OwnerRequest request) {
        Owner owner = ownerMapper.requestToEntity(request);
        return ownerRepo.save(owner).getId();
    }

    @Override
    public Long update(OwnerRequest request, Long aLong) {
        return null;
    }

    @Override
    public List<OwnerResponse> read(String isActive) {
        List<Owner> owners = ownerRepo.getAll(isActive);
        return owners.stream()
                .map(ownerMapper::entityToResponse)
                .toList();
    }

    @Override
    public void delete(Long aLong) {
        ownerRepo.toggleActiveStatus(aLong);
    }

    @Override
    public boolean hardDelete(Long aLong) {
        return false;
    }

    @Override
    public Owner model(Long aLong) {
        return ownerRepo.findById(aLong).orElseThrow();
    }

    @Override
    public Boolean terminateContract(Long contractId) {
        contractRepo.terminateContractById(contractId);
        return Boolean.TRUE;
    }
}
