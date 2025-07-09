package com.tms.easyrento.model.auth;

import com.tms.easyrento.admin.Policy;
import com.tms.easyrento.model.AbstractAuditor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author shashi
 * @version 1.0.0
 * @since 3/11/24 2:01 AM
 */
@Entity
@Getter
@Setter
@Table(name = "roles",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_roles_name", columnNames = "name")
        }
)
@NoArgsConstructor
public class Role extends AbstractAuditor {

    @Id
    @SequenceGenerator(name = "roles_seq_gen", sequenceName = "roles_seq", allocationSize = 1)
    @GeneratedValue(generator = "roles_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_account_id"),
            foreignKey = @ForeignKey(name = "fk_roles_user-accounts_id"),
            inverseForeignKey = @ForeignKey(name = "fk_user_accounts_roles_id")
    )
    private Set<UserAccount> userAccounts = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "role_policy",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "policy_id"),
            foreignKey = @ForeignKey(name = "fk_roles_policies_id"),
            inverseForeignKey = @ForeignKey(name = "fk_policies_roles_id")
    )
    private Set<Policy> policies = new HashSet<>();

    // for mapping dto to Role object
    public Role(Long id) {
        this.id = id;
    }

}
