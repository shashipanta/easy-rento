package com.tms.easyrento.model.owner;

import com.tms.easyrento.model.AbstractAuditor;
import com.tms.easyrento.model.Address;
import com.tms.easyrento.model.contract.Contract;
import com.tms.easyrento.model.property.Property;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/6/23 3:34 AM
 */

@Entity
@Getter
@Setter
@Table(name = "owners")
public class Owner extends AbstractAuditor {

    @Id
    @SequenceGenerator(name = "owners_seq_gen", sequenceName = "owners_seq")
    @GeneratedValue(generator = "owners_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "name_np", length = 200, nullable = false)
    private String nameNp;

    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Property> property;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_owners_address_id"))
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private List<Contract> contract;
}
