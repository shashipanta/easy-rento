package com.tms.easyrento.repository;

import com.tms.easyrento.model.contract.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/30/23 11:41 PM
 */
public interface ContractRepo extends JpaRepository<Contract, Long> {


    @Modifying
    @Query(nativeQuery = true, value = "update contracts c set c.owner_terminated = true where c.id = ?1")
    void terminateContractById(Long contractId);

    @Modifying
    @Query(nativeQuery = true,
    value = "update contracts c set c.tenant_terminated = true where c.id = ?1")
    void terminateTenantContractByContractId(Long contractId);

    @Modifying
    @Query(nativeQuery = true,
            value = "update contracts c set c.contract_terminated = true , c.termination_remarks = ?2 where c.id = ?1")
    boolean contractFinalTermination(Long contractId, String terminationRemarks);


    @Query(nativeQuery = true,
    value = "UPDATE contracts c SET c.status = 'APPROVED' WHERE c.id = ?1")
    void approveContract(Long contractId);

}
