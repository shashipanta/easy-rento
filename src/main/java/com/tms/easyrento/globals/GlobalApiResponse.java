package com.tms.easyrento.globals;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/5/23 11:29 PM
 */

@Getter
@Setter
@Builder
public class GlobalApiResponse {

    private String message;
    private Object data;
    private String status;
}
