package com.tms.easyrento.enums;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/29/24 10:14 PM
 */
public enum UserType {

    OWNER("O"),
    TENANT("T");

    private String userTypeName;

    UserType(String userTypeName) {
        this.userTypeName = userTypeName;
    }
}
