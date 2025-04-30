package com.tms.easyrento.model.auth;

import com.tms.easyrento.model.AbstractAuditor;
import jakarta.persistence.*;

/**
 * @author shashi
 * @version 1.0.0
 * @since 3/16/24 1:03 AM
 */

@Entity
@Table(name = "user_roles")
public class UserRole extends AbstractAuditor {

    @Id
    @SequenceGenerator(name = "user_roles_seq_gen", sequenceName = "user_roles_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_roles_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;


}
