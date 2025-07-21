package com.tms.easyrento.dto.response;

import com.tms.easyrento.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-11 16:54
 */

@Getter
@Setter
public class AbstractAuditorResponse {

    private Long createdBy;

    private Timestamp createdOn;

    private Long lastModifiedBy;

    private Timestamp lastModifiedOn;

    private boolean active = true;

    private Status status = Status.APPROVED;

}
