package com.tms.easyrento.dto.request;

import com.tms.easyrento.constants.FieldErrorConstants;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-19 15:44
 */

@Getter
@Setter
public class RoomRequest {

    private Long id;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private String roomName;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private Short totalRooms;
}
