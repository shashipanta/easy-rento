package com.tms.easyrento.model.contract;

import com.tms.easyrento.model.AbstractAuditor;
import com.tms.easyrento.model.owner.Owner;
import com.tms.easyrento.model.tenant.Tenant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/11/23 11:46 PM
 */

@Getter
@Setter
@Entity
@Table(name = "contracts")
public class Contract extends AbstractAuditor {

    @Id
    @SequenceGenerator(name = "contracts_seq_gen", sequenceName = "contracts_seq", allocationSize = 1)
    @GeneratedValue(generator = "contracts_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = ALL)
    @JoinColumn(name = "tenant_id", foreignKey = @ForeignKey(name = "fk_contracts_tenants_id"))
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {ALL})
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name = "fk_contracts_owners_id"))
    private Owner owner;

    // this might cause sync issue as table migit change names

//    @Column(name = "tenant_full_name", nullable = false, length = 200)
//    private String tenantFullName;
//
//    @Column(name = "owner_full_name", nullable = false, length = 200)
//    private String ownerFullName;

    @Column(name = "contract_start_date", nullable = false)
    private String contractStartDate;

    @Column(name = "contract_duration", nullable = false)
    private Long contractDuration = 3L;

    @Column(name = "rent_per_month", nullable = false)
    private Long perMonthRent;

    @Column(name = "contract_file_location")
    private String contractFileLocation;

    @Column(name = "contract_terminated")
    private Boolean contractTerminated = false;

    @Column(name = "termination_remarks")
    private String terminationRemarks;

    @Column(name = "owner_terminated")
    private Boolean ownerTerminated;

    @Column(name = "tenant_terminated")
    private Boolean tenantTerminated;
}
