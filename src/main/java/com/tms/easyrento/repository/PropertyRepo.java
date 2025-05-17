package com.tms.easyrento.repository;

import com.tms.easyrento.model.property.Property;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/7/23 9:57 PM
 */
@Repository
public interface PropertyRepo extends JpaRepository<Property, Long> {

    @Query(value = "SELECT *\n" +
            "FROM properties p\n" +
            "WHERE \n" +
            "    CASE WHEN ?1 = '1' THEN p.active = true\n" +
            "         WHEN ?1 = '0' THEN p.active = false \n" +
            "         ELSE p.active = p.active \n" +
            "    END", nativeQuery = true)
    List<Property> findByActive(String isActive);


    @Transactional
    @Modifying
    @Query(value = "UPDATE properties p SET active = not p.active WHERE p.id = ?1", nativeQuery = true)
    void toggleActiveStatus(Long id);


    @Query(nativeQuery = true,
    value = """
                select *
                from properties p
                where p.owner_id = ?
                  and p.active = true
            """)
    List<Property> getPropertyByOwnerId(Long ownerId);
}
