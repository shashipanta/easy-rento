package com.tms.easyrento.service.impl;

import com.tms.easyrento.dto.request.TenantRequest;
import com.tms.easyrento.dto.response.TenantResponse;
import com.tms.easyrento.mappers.TenantMapper;
import com.tms.easyrento.model.tenant.Tenant;
import com.tms.easyrento.repository.ContractRepo;
import com.tms.easyrento.repository.TenantRepo;
import com.tms.easyrento.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/12/23 1:02 AM
 */

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepo tenantRepo;
    private final TenantMapper tenantMapper;
    private final ContractRepo contractRepo;

    @Override
    public Long create(TenantRequest request) {
        Tenant tenant = tenantMapper.requestToEntity(request);
        return tenantRepo.save(tenant).getId();
    }

    @Override
    public Long update(TenantRequest request, Long aLong) {
        return null;
    }

    @Override
    public List<TenantResponse> read(String isActive) {

        List<Tenant> tenants = tenantRepo.getAll(isActive);
        return tenants.stream()
                .map(tenantMapper::entityToResponse)
                .toList();
    }

    @Override
    public TenantResponse read(Long aLong) {
        return tenantMapper.entityToResponse(tenantRepo.findById(aLong).orElseThrow());
    }

    @Override
    public void delete(Long aLong) {
        tenantRepo.toggleActiveStatus(aLong);
    }

    @Override
    public boolean hardDelete(Long aLong) {
        return false;
    }

    @Override
    public Tenant model(Long aLong) {
        return null;
    }

    @Override
    public Boolean terminateContract(Long contractId) {
        contractRepo.terminateTenantContractByContractId(contractId);
        return Boolean.TRUE;
    }
}
