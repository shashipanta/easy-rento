package com.tms.easyrento.model.propertyOwnership;

import com.tms.easyrento.model.AbstractAuditor;
import com.tms.easyrento.model.owner.Owner;
import com.tms.easyrento.model.property.Property;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-11 16:25
 */

@Getter
@Setter
@Entity
@Table(name = "property_ownerships")
public class PropertyOwnership extends AbstractAuditor {

    private static final String FK_PREFIX = "fk_property-ownerships_";
    private static final String FK_OWNER = FK_PREFIX + "owner";
    private static final String FK_PROPERTY = FK_PREFIX + "property";

    @EmbeddedId
    private PropertyOwnershipId id = new PropertyOwnershipId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ownerId")
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name = FK_OWNER))
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("propertyId")
    @JoinColumn(name = "property_id", foreignKey = @ForeignKey(name = FK_PROPERTY))
    private Property property;

    @Column(name = "ownership_percentage")
    private Double ownershipPercentage;

    @Column(name = "ownership_start_date")
    private LocalDate startDate;
}
