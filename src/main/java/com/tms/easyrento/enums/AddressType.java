package com.tms.easyrento.enums;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/6/23 8:32 PM
 */
public enum AddressType {

    P("PROPERTY", "PROPERTY-NEPALI"),
    O("OWNER", "OWNER-NEPALI"),
    T("TENANT", "TENANT-NEPALI");

    AddressType(String addressName, String addressNameNp) {
        this.addressName = addressName;
        this.addressNameNp = addressNameNp;
    }

    private String addressName;
    private String addressNameNp;
}
