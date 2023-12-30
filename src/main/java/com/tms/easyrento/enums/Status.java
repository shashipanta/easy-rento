package com.tms.easyrento.enums;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/5/23 11:40 PM
 */
public enum Status {

    APPROVED("APPROVED", "APPROVED_NP"),
    UNDER_APPROVAL("UDER_APPROVAL", "UNDER_APPROVAL_NP");

    Status(String name, String nameNp) {
        this.name = name;
        this.nameNp = nameNp;
    }

    private String name;
    private String nameNp;
}
