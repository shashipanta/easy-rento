package com.tms.easyrento.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/7/23 10:20 PM
 */

@Getter
@Setter
public class AddressResponse {

    private Long id;
    private Short wardNo;
    private String addressType;
    private String streetName;
    private String streetNameNp;
    private String googleLocation;
}
