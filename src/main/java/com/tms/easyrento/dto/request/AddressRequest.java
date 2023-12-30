package com.tms.easyrento.dto.request;

import com.tms.easyrento.enums.AddressType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/7/23 10:20 PM
 */

@Getter
@Setter
public class AddressRequest {

    private Long id;
    private Long wardNo;
    private String addressType;
    private String streetName;
    private String streetNameNp;
    private String googleLocation;


}
