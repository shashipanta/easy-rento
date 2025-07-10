package com.tms.easyrento.repository;

import com.tms.easyrento.model.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/8/23 6:05 AM
 */

@Repository
public interface OwnerRepo extends JpaRepository<Owner, Long> {

    @Query(value = "SELECT * \n" +
            "FROM owners o \n" +
            "WHERE CASE\n" +
            "          WHEN ?1 = '1' then o.active = true \n" +
            "          WHEN ?1 = '0' then o.active = false \n" +
            "          ELSE o.active = o.active \n" +
            "          END", nativeQuery = true)
    List<Owner> getAll(String isActive);

    @Modifying
    @Query(value = "UPDATE owners o SET o.active = not o.active WHERE o.id = ?1", nativeQuery = true)
    void toggleActiveStatus(Long id);

    @Query(value = "select o.ID from owners o where o.contract_id = ?1", nativeQuery = true)
    List<Owner> getAssociatedOwnersByContractId(Long contractId);

    Optional<Owner> findByUserAccount_Id(Long userAccountId);
}
