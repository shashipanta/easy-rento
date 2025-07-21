package com.tms.easyrento.enums;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-19 15:26
 */

public enum RoomType {

    LIVING_ROOM("Living Room", "Living Room Np"),
    KITCHEN_ROOM("Kitchen Room", "Kitchen Room Np"),
    BED_ROOM("Bed Room", "Bed Room Np"),
    EXTERNAL_BATHROOM("External Bathroom", "External Bathroom Np"),
    BATH_ROOM("Bath Room","Bath Room Np");

    private String name;
    private String nameNp;

    RoomType(String name, String nameNp) {
        this.name = name;
        this.nameNp = nameNp;
    }
}
