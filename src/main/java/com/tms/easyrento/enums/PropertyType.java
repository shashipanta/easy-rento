package com.tms.easyrento.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/6/23 3:39 AM
 */
@Getter
public enum PropertyType {

    HOUSE("HOUSE", "NEPALI-HOUSE"),
    APARTMENT("APARTMENT", "NEPALI-APARTMENT"),
    LAND("LAND", "NEPALI-LAND"),
    ROOM("ROOM", "NEPALI-ROOM"),
    FLAT("FLAT", "NEPALI-FLAT");

    private String typeName;
    private String typeNameNp;

    PropertyType(String typeName, String typeNameNp){
        this.typeName = typeName;
        this.typeNameNp = typeNameNp;
    }

    public static List<String> getAvailablePropertyTypes() {
        List<String> propertyTypes = new ArrayList<>();
        for(PropertyType pt: PropertyType.values())
            propertyTypes.add(pt.typeName);
        return propertyTypes;
    }




}
