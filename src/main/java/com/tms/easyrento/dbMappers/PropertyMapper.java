package com.tms.easyrento.dbMappers;

import com.tms.easyrento.dto.response.PropertyResponse;
import com.tms.easyrento.dto.response.SinglePropertyResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 2/6/24 11:32 AM
 */
@Mapper
public interface PropertyMapper {

    PropertyResponse findSinglePropertyByOwnerId(Long ownerId, Long propertyId);

    List<PropertyResponse> findActiveList(String isActive);

    List<PropertyResponse> findPropertiesByOwnerId(Long ownerId);

    List<SinglePropertyResponse> getSinglePropertyForAdmin(Long propertyId);
}
