package com.tms.easyrento.model.owner;

import com.tms.easyrento.model.AbstractAuditor;
import com.tms.easyrento.model.Address;
import com.tms.easyrento.model.auth.UserAccount;
import com.tms.easyrento.model.contract.Contract;
import com.tms.easyrento.model.property.Property;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/6/23 3:34 AM
 */

@Getter
@Setter
@Entity
@Table(name = "owners",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_owners_user-account_id", columnNames = "user_id"),
                @UniqueConstraint(name = "uk_owners_address_id", columnNames = "address_id")
        })
@NoArgsConstructor
public class Owner extends AbstractAuditor {

    @Id
    @SequenceGenerator(name = "owners_seq_gen", sequenceName = "owners_seq")
    @GeneratedValue(generator = "owners_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "name_np", length = 200, nullable = false)
    private String nameNp;

    @ManyToMany(mappedBy = "owners")
    private List<Property> property;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_owner_address_id"))
    private Address address;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_owner_user-account_id"))
    private UserAccount userAccount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private List<Contract> contract;

    public Owner(Long userId, String name, String nameNp) {
        this.userAccount = new UserAccount(userId);
        this.name = name;
        this.nameNp = nameNp;
    }
}
