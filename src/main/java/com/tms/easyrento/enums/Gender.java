package com.tms.easyrento.enums;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/6/23 3:27 AM
 */
public enum Gender {

    MALE("MALE", "NEPALI-MALE"),
    FEMALE("FEMALE", "NEPALI-FEMALE"),
    OTHERS("OTHERS", "OTHERS");
    private String name;
    private String nameNp;

    Gender(String name, String nameNp) {
        this.name = name;
        this.nameNp = nameNp;
    }
}
