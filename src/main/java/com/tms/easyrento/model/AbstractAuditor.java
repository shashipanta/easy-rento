package com.tms.easyrento.model;

import com.tms.easyrento.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/5/23 11:34 PM
 */

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public class AbstractAuditor {

    @CreatedBy
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @CreatedDate
    @Column(name = "created_on", nullable = false)
    private Timestamp createdOn;

    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false)
    private Long lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_on", nullable = false)
    private Timestamp lastModifiedOn;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.APPROVED;
}
