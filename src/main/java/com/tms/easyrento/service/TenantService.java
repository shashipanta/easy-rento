package com.tms.easyrento.service;

import com.tms.easyrento.dto.request.TenantRequest;
import com.tms.easyrento.dto.response.TenantResponse;
import com.tms.easyrento.model.tenant.Tenant;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/12/23 12:58 AM
 */
public interface TenantService extends CurdService<TenantRequest, Tenant, TenantResponse, Long >{

    Boolean terminateContract(Long contractId);
}
