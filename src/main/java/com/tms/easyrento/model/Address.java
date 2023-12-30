package com.tms.easyrento.model;

import com.tms.easyrento.enums.AddressType;
import com.tms.easyrento.model.AbstractAuditor;
import com.tms.easyrento.model.owner.Owner;
import com.tms.easyrento.model.property.Property;
import com.tms.easyrento.model.tenant.Tenant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/6/23 4:03 AM
 */
@Entity
@Getter
@Setter
@Table(name = "addresses")
public class Address extends AbstractAuditor {
    @Id
    @SequenceGenerator(name = "addresses_seq_gen", sequenceName = "addresses_seq", allocationSize = 1)
    @GeneratedValue(generator = "addresses_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "ward_no", nullable = false)
    private Long wardNo;

    @Column(name = "address_type")
    @Enumerated(EnumType.STRING)
    private AddressType addressType = AddressType.T;

    @Column(name = "street_name", length = 200)
    private String streetName;

    @Column(name = "street_name_np", length = 200)
    private String streetNameNp;

    @Column(name = "google_location")
    private String googleLocation;

    @OneToOne(mappedBy = "address")
    private Owner owner;

}
