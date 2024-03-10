package com.tms.easyrento.model.auth;

import com.tms.easyrento.enums.UserType;
import com.tms.easyrento.model.AbstractAuditor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


/**
 * @author shashi
 * @version 1.0.0
 * @since 12/5/23 11:50 PM
 */

@Entity
@Getter
@Setter
@Table(name = "user_accounts",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user-accounts_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_user-accounts_user-name", columnNames = "user_name")
        }
)
public class UserAccount extends AbstractAuditor {

    @Id
    @SequenceGenerator(name = "user_accounts_seq_gen", sequenceName = "user_accounts_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_accounts_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 200)
    private String username;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType = UserType.TENANT;
}
