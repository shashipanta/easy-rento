package com.tms.easyrento.model.property;

import com.tms.easyrento.enums.PropertyType;
import com.tms.easyrento.model.AbstractAuditor;
import com.tms.easyrento.model.Address;
import com.tms.easyrento.model.file.PropertyImage;
import com.tms.easyrento.model.owner.Owner;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/6/23 3:37 AM
 */

@Entity
@Getter
@Setter
@Table(name = "properties",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_properties_property-code", columnNames = "property_code"),
                @UniqueConstraint(name = "uk_properties_address-id", columnNames = {"address_id"})
        }
)
@NamedEntityGraph(name = "property-owner-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("owner")
        }
)
public class Property extends AbstractAuditor {

    @Id
    @SequenceGenerator(name = "properties_seq_gen", sequenceName = "properties_seq", allocationSize = 1)
    @GeneratedValue(generator = "properties_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "property_code", nullable = false)
    private String propertyCode;

//    @Column(name = "image_url", columnDefinition = "text")
//    private String imageUrl;

    @Column(name = "property_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType = PropertyType.ROOM;

    @Column(name = "allocated_price", nullable = false)
    private Long allocatedPrice;

    @Column(name = "price_per_unit", nullable = false)
    private Long pricePerUnit;

    @Column(name = "is_system_evaluated_price")
    private boolean isSystemEvaluatedPrice = false;

    @Column(name = "dynamic_price")
    private Long dynamicPrice;

    @Column(name = "total_rooms")
    private Short totalRooms;

    @Column(name = "total_bed_rooms")
    private Short totalBedRooms = 1;

    @Column(name = "total_living_rooms")
    private Short totalLivingRooms = 1;

    @Column(name = "total_bath_rooms")
    private Short totalBathRooms = 1;

    @Column(name = "occupied")
    private boolean occupied = false;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false, foreignKey = @ForeignKey(name = "fk_properties_owners_id"))
    private Owner owner;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", foreignKey = @ForeignKey(name = "fk_properties_addresses_id"))
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PropertyImage> propertyImage;
}
