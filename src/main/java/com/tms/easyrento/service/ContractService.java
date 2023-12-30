package com.tms.easyrento.service;

import com.tms.easyrento.dto.request.ContractRequest;
import com.tms.easyrento.dto.response.ContractResponse;
import com.tms.easyrento.model.contract.Contract;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/30/23 11:38 PM
 */
public interface ContractService extends CurdService<ContractRequest,Contract, ContractResponse,Long >{

    boolean terminateContract(Long contractId, String remarks);

    boolean approveContract(Long contractId);
}
