package com.tms.easyrento.repository;

import com.tms.easyrento.model.contract.Contract;
import com.tms.easyrento.model.tenant.Tenant;
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


    @Modifying
    @Query(nativeQuery = true,
    value = "UPDATE contracts c SET c.status = 'APPROVED' WHERE c.id = ?1")
    void approveContract(Long contractId);

    @Query(nativeQuery = true,
    value = """
                select t.*
                from tenants t
                         inner join contracts c on t.id = c.tenant_id
                         inner join owners o on c.owner_id = o.id
                where o.id = ?1
            """)
    List<Long> getAssociatedTenantId(Long ownerId);

}
