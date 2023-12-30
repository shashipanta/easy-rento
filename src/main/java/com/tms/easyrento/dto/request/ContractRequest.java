package com.tms.easyrento.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/30/23 11:40 PM
 */

@Getter
@Setter
public class ContractRequest {

    private Long id;
    private String contractDuration;
    private List<Long> ownerIds;
    private List<Long> tenantIds;
    private String tenantFullName;
    private String ownerFullName;
    private String contractStartDate;
    private Long rentPerMonth;


}
