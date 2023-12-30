package com.tms.easyrento.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/6/23 8:55 PM
 */
@Getter
@Setter
public class OwnerRequest {

    private Long id;

    private String name;
    private String nameNp;

    private boolean isActive;

}
