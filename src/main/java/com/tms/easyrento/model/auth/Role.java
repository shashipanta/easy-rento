package com.tms.easyrento.model.auth;

import com.tms.easyrento.model.AbstractAuditor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
public class Role extends AbstractAuditor {

    @Id
    @SequenceGenerator(name = "roles_seq_gen", sequenceName = "roles_seq", allocationSize = 1)
    @GeneratedValue(generator = "roles_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"),
            foreignKey = @ForeignKey(name = "fk_roles_permissions_id"),
            inverseForeignKey = @ForeignKey(name = "fk_permissions_roles_id")
    )
    private List<Permission> permissions;

}
