package com.tms.easyrento.admin;

import com.tms.easyrento.model.auth.Permission;
import com.tms.easyrento.model.auth.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-06-28 10:40
 */

@Getter
@Setter
@Entity
@Table(
        name = "policies",
        uniqueConstraints = {@UniqueConstraint(name = "uk_policies_name", columnNames = {"name", "http_method"})}
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Policy {

    @Id
    @SequenceGenerator(name = "policies_seq_gen", sequenceName = "policies_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "policies_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "http_method", nullable = false)
    private String httpMethod = "GET";

    @Column(name = "resource_pattern", nullable = false)
    private String resourcePattern;

    @Column(name = "controller_name", nullable = false)
    private String controllerName;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "active", nullable = false)
    private boolean active = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "policy_permission",
            joinColumns = @JoinColumn(name = "policy_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"),
            foreignKey = @ForeignKey(name = "fk_policies_permissions_id"),
            inverseForeignKey = @ForeignKey(name = "fk_permissions_policies_id")
    )
    private Set<Permission> permissions;

    @ManyToMany(mappedBy = "policies")
    private Set<Role> roles;

}
