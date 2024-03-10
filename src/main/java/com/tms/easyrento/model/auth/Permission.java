package com.tms.easyrento.model.auth;

import com.tms.easyrento.model.AbstractAuditor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author shashi
 * @version 1.0.0
 * @since 3/11/24 2:05 AM
 */

@Entity
@Getter
@Setter
@Table(name = "permissions",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_permissions_name", columnNames = "permission_name")
        }
)
public class Permission extends AbstractAuditor {

        @Id
        @SequenceGenerator(name = "permissions_seq_gen", sequenceName = "permissions_seq", allocationSize = 1)
        @GeneratedValue(generator = "permissions_seq_gen", strategy = GenerationType.SEQUENCE)
        private Long id;

        @Column(name = "name", nullable = false, length = 150)
        private String name;

        @Column(name = "alias", nullable = false, length = 10)
        private String alias;

        @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
        private Set<Role> role;
}
