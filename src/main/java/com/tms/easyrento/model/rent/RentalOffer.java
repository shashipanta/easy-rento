package com.tms.easyrento.model.rent;

import com.tms.easyrento.model.AbstractAuditor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/28/24 10:42 AM
 */

@Entity
@Getter
@Setter
@Table(name = "rent_offers")
public class RentalOffer extends AbstractAuditor {

    @Id
    @SequenceGenerator(name = "rent_offers_seq_gen", sequenceName = "rent_offers_seq", allocationSize = 1)
    @GeneratedValue(generator = "rent_offers_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "property_id", nullable = false)
    private Long propertyId;

    @Column(name = "requested_price")
    private Long requestedPrice;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "is_accepted", nullable = false)
    boolean isAccepted = false;

}
