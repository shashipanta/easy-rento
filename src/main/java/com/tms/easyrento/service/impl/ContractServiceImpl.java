package com.tms.easyrento.service.impl;

import com.tms.easyrento.dto.request.ContractRequest;
import com.tms.easyrento.dto.response.ContractResponse;
import com.tms.easyrento.dto.response.TenantResponse;
import com.tms.easyrento.mappers.ContractMapper;
import com.tms.easyrento.mappers.TenantMapper;
import com.tms.easyrento.model.contract.Contract;
import com.tms.easyrento.model.owner.Owner;
import com.tms.easyrento.model.tenant.Tenant;
import com.tms.easyrento.repository.ContractRepo;
import com.tms.easyrento.repository.OwnerRepo;
import com.tms.easyrento.repository.TenantRepo;
import com.tms.easyrento.service.ContractService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/30/23 11:41 PM
 */
@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepo contractRepo;
    private final OwnerRepo ownerRepo;

    private final ContractMapper contractMapper;
    private final TenantRepo tenantRepo;
    private final TenantMapper tenantMapper;

    @Override
    public Long create(ContractRequest request) {
        Contract contract = contractMapper.requestToEntity(request);
        return contractRepo.save(contract).getId();
    }

    @Override
    public Long update(ContractRequest request, Long aLong) {
        Contract persistedContract = contractRepo.findById(aLong).orElseThrow();
        Contract partialUpdate = contractMapper.partialUpdate(request, persistedContract);
        return partialUpdate.getId();
    }

    @Override
    public List<ContractResponse> read(String isActive) {
        return null;
    }

    @Override
    public ContractResponse read(Long aLong) {
        return contractMapper.entityToResponse(contractRepo.findById(aLong)
                .orElseThrow());
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public boolean hardDelete(Long aLong) {
        return false;
    }

    @Override
    public Contract model(Long aLong) {
        return null;
    }

    @Override
    public boolean terminateContract(Long contractId, String remarks) {
        // validate if all stakeholders have agreed to terminate contract
        boolean allStakeHoldersVerified = verifyStakeHolderConfirmation(contractId);
        return allStakeHoldersVerified? contractRepo.contractFinalTermination(contractId, remarks): Boolean.FALSE;
    }

    private Boolean verifyStakeHolderConfirmation(Long contractId) {
        List<Owner> owners = ownerRepo.getAssociatedOwnersByContractId(contractId);
        List<Tenant> tenants = tenantRepo.getAssociatedTenantsByContractId(contractId);

        // perform database query to verify termination

        // if owner and tenant has termination then update status to TERMINATED

//        return !(allTenantsNotRejected && allOwnersNotRejected);

        return true;
    }

    @Override
    public boolean approveContract(Long contractId) {
        contractRepo.approveContract(contractId);
        return Boolean.TRUE;
    }

    @Override
    public List<TenantResponse> getAssociatedTenants(Long ownerId) {
        // call repo
        List<Long> associatedTenantId = contractRepo.getAssociatedTenantId(ownerId);

        return associatedTenantId.stream()
                .map(this::toTenantResponse)
                .collect(Collectors.toList());
    }

    private TenantResponse toTenantResponse(Long id) {
        Tenant tenant = tenantRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Tenant not found!"));
        return tenantMapper.entityToResponse(tenant);
    }
}
