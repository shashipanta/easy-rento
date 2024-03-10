package com.tms.easyrento.repository;

import com.tms.easyrento.model.rent.RentalOffer;
import com.tms.easyrento.model.rent.RentalOfferProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/28/24 10:58 AM
 */
public interface RentalOfferRepo extends JpaRepository<RentalOffer, Long> {

    @Query(nativeQuery = true,
            value = """
                    SELECT t.id                 as tenantId,
                           t.email              as tenantEmail,
                           t.gender             as tenantGender,
                           t.name               as tenantName,
                           t.address_id         as tenantAddressId,
                        
                           o.id                 as ownerId,
                           o.name               as ownerName,
                           o.address_id         as ownerAddressId,
                        
                           r_o.id               as rentalOfferId,
                           r_o.status           as rentalOfferStatus,
                           r_o.is_accepted      as rentalOfferAccepted,
                           r_o.requested_price  as rentalOfferRequestedPrice,
                           
                           p.id                 as propertyId,
                           p.title              as propertyTitle,
                           p.allocated_price    as propertyAllocatedPrice,
                           p.dynamic_price      as propertyDynamicPrice
                        
                    FROM rent_offers r_o
                        inner join owners     o on r_o.owner_id    = o.id
                        inner join tenants    t on r_o.tenant_id   = t.id
                        inner join properties p on r_o.property_id = p.id
                    where r_o.owner_id = ?
                        and r_o.status <> 'REJECTED'
                    """)
    List<RentalOfferProjection> offers(Long ownerId);


    @Query(nativeQuery = true,
    value = """
            select count(ro.id)
            from rent_offers ro
            where ro.owner_id = ?
            """)
    Long rentalOfferCount(Long ownerId);
}
