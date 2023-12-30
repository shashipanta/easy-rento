package com.tms.easyrento.globals;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/5/23 11:28 PM
 */
public class BaseController {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    public GlobalApiResponse successResponse(String message, Object data) {
        return GlobalApiResponse
                .builder()
                .message(message)
                .data(data)
                .status(SUCCESS)
                .build();
    }

    public GlobalApiResponse failureResponse(String message, Object data) {
        return GlobalApiResponse
                .builder()
                .message(message)
                .data(data)
                .status(FAILURE)
                .build();
    }

}
