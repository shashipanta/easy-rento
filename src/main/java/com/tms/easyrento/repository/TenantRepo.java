package com.tms.easyrento.repository;

import com.tms.easyrento.model.owner.Owner;
import com.tms.easyrento.model.tenant.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/12/23 10:16 PM
 */
@Repository
public interface TenantRepo extends JpaRepository<Tenant, Long> {

    @Query(value = "SELECT * \n" +
            "FROM tenants t \n" +
            "WHERE CASE\n" +
            "          WHEN ?1 = '1' then t.active = true \n" +
            "          WHEN ?1 = '0' then t.active = false \n" +
            "          ELSE t.active = t.active \n" +
            "          END", nativeQuery = true)
    List<Tenant> getAll(String isActive);

    @Modifying
    @Query(value = "UPDATE tenant t SET t.active = not t.active WHERE t.id = ?1", nativeQuery = true)
    void toggleActiveStatus(Long id);

    @Query(nativeQuery = true,
    value = "SELECT t.ID FROM TENANTS t WHERE t.CONTRACT_ID = ?1")
    List<Tenant> getAssociatedTenantsByContractId(Long contractId);
}
