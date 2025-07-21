package com.tms.easyrento.model.propertyOwnership;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-11 16:22
 */

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyOwnershipId implements Serializable {

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "property_id")
    private Long propertyId;

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, propertyId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(!(obj instanceof PropertyOwnershipId other)) return false;
        return Objects.equals(ownerId, other.ownerId) &&
                Objects.equals(propertyId, other.propertyId);
    }
}
