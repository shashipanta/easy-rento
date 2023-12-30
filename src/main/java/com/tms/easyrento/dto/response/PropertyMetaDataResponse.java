package com.tms.easyrento.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/9/23 3:28 AM
 */

@Getter
@Setter
@Builder
public class PropertyMetaDataResponse {

    List<String> propertyType;
    List<OwnerResponse> ownerResponses;
}
