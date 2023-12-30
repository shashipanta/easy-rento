package com.tms.easyrento.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/12/23 12:59 AM
 */

@Getter
@Setter
public class TenantResponse {

    private Long id;

    private String name;

    private String nameNp;

    private String gender;

    private Long netWorth;

    private String occupation;

    private String phoneNo;

    private AddressResponse address;


}
