package com.tms.easyrento.service.impl;

import com.tms.easyrento.dto.request.ContractRequest;
import com.tms.easyrento.dto.response.ContractResponse;
import com.tms.easyrento.mappers.ContractMapper;
import com.tms.easyrento.model.contract.Contract;
import com.tms.easyrento.model.owner.Owner;
import com.tms.easyrento.model.tenant.Tenant;
import com.tms.easyrento.repository.ContractRepo;
import com.tms.easyrento.repository.OwnerRepo;
import com.tms.easyrento.repository.TenantRepo;
import com.tms.easyrento.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

        Boolean allOwnersNotRejected = owners.stream()
                .anyMatch(owner -> !owner.getContract().getOwnerTerminated());
        Boolean allTenantsNotRejected = tenants.stream()
                .anyMatch(tenant -> !tenant.getContract().getTenantTerminated());

        return !(allTenantsNotRejected && allOwnersNotRejected);

    }

    @Override
    public boolean approveContract(Long contractId) {
        contractRepo.approveContract(contractId);
        return Boolean.TRUE;
    }
}
