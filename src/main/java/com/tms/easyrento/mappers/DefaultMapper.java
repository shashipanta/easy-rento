package com.tms.easyrento.mappers;

import org.mapstruct.*;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/7/23 10:02 PM
 */

@MapperConfig
public interface DefaultMapper<REQ, MOD, RES, ID> {

    MOD requestToEntity(REQ requestDto);

    REQ toDto(MOD model);


    @Mapping(source = "active", target = "isActive")
    @Mapping(source = "createdOn", target = "createdOn", dateFormat = "dd-MM-yyyy HH:mm:ss")
    RES entityToResponse(MOD model);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MOD partialUpdate(REQ requestDto, @MappingTarget MOD model);

}
