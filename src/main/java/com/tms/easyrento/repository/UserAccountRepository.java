package com.tms.easyrento.repository;

import com.tms.easyrento.model.auth.UserAccount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/5/23 11:56 PM
 */

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_accounts ua SET active = not ?2 WHERE ua.id = ?1", nativeQuery = true)
    void toggleIsActive(Long id, boolean isActive);


    @Query(value = "SELECT * \n" +
            "FROM user_accounts ua \n" +
            "WHERE CASE\n" +
            "          WHEN ?1 = 1 then ua.active = true \n" +
            "          WHEN ?1 = 0 then ua.active = false \n" +
            "          ELSE ua.active = ua.active \n" +
            "          END", nativeQuery = true)
    List<UserAccount> getAll(Long isActive);

    @Query(nativeQuery = true,
    value = "SELECT * FROM user_accounts u_a where u_a.user_name = ?1 or u_a.email = ?1")
    Optional<UserAccount> findBy(String username);
}
