package com.tms.easyrento.repository;

import com.tms.easyrento.model.propertyOwnership.PropertyOwnership;
import com.tms.easyrento.model.propertyOwnership.PropertyOwnershipId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PropertyOwnershipRepo extends JpaRepository<PropertyOwnership, PropertyOwnershipId> {

    // Find all ownership by a specific owner ID
    List<PropertyOwnership> findById_OwnerId(Long ownerId);

    // Find all ownerships for a specific property
    List<PropertyOwnership> findById_PropertyId(Long propertyId);

    // Delete a specific ownership by owner and property ID
    void deleteById_OwnerIdAndId_PropertyId(Long ownerId, Long propertyId);


    // interface based projection
    @Query("""
            SELECT
                p_o.id.ownerId                  AS ownerId,
                p_o.id.propertyId               AS propertyId,
                p_o.ownershipPercentage         AS ownershipPercentage,
                p_o.startDate                   AS startDate
            from PropertyOwnership p_o
                where p_o.id.ownerId = :ownerId
            """)
    List<OwnershipDto> findOwnershipsByOwnerId(@Param("ownerId") Long ownerId);

    interface OwnershipDto {
        Long getOwnerId();
        Long getPropertyId();
        Double getOwnershipPercentage();
        LocalDate getStartDate();
    }

    @Query("""
            SELECT new com.tms.easyrento.dto.projection.PropertyOwnershipDto (
                        p_o.id.ownerId,
                        p_o.id.propertyId,
                        p_o.ownershipPercentage,
                        p_o.startDate
                        )
            FROM PropertyOwnership p_o
            WHERE p_o.id.propertyId = :propertyId
            """)
    List<OwnershipDto> findOwnershipsByPropertyId(@Param("propertyId") Long propertyId);

}
