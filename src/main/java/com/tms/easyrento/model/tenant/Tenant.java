package com.tms.easyrento.model.tenant;

import com.tms.easyrento.enums.Gender;
import com.tms.easyrento.model.AbstractAuditor;
import com.tms.easyrento.model.Address;
import com.tms.easyrento.model.contract.Contract;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.CascadeType.ALL;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/6/23 3:18 AM
 */

@Entity
@Getter
@Setter
@Table(name = "tenants",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tenants_phone-no", columnNames = "phone_no"),
                @UniqueConstraint(name = "uk_tenants_email", columnNames = "email")
        }
)
public class Tenant extends AbstractAuditor {

        @Id
        @SequenceGenerator(name = "tenants_seq_gen", sequenceName = "tenants_seq", allocationSize = 1)
        @GeneratedValue(generator = "tenants_seq_gen", strategy = GenerationType.SEQUENCE)
        private Long id;

        @Column(name = "name", length = 200, nullable = false)
        private String name;

        @Column(name = "name_np", length = 200, nullable = false)
        private String nameNp;

        @Column(name = "email", length = 150, nullable = false)
        private String email;

        @Column(name = "phone_no", length = 10, nullable = false)
        private String phoneNo;

        @Column(name = "occupation", length = 200)
        private String occupation;

        @Column(name = "gender")
        @Enumerated(EnumType.STRING)
        private Gender gender;

        @Column(name = "net_worth", nullable = false)
        private Long netWorth;

        // profile

        // documents

        @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true,
                targetEntity = Address.class, cascade = {ALL})
        @JoinColumn(name = "address_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_tenants_addresses_id"))
        private Address address;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "contract_id", foreignKey = @ForeignKey(name = "fk_tenants_contracts_id"))
        private Contract contract;

}
