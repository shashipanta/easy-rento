package com.tms.easyrento.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-21 16:14
 */

@Getter
@Setter
public class RoomResponse {
    private Long id;
    private String roomName;
    private Short totalRooms;
}
