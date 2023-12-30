package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.UserRequest;
import com.tms.easyrento.dto.response.UserResponse;
import com.tms.easyrento.model.AbstractAuditor;
import com.tms.easyrento.model.UserAccount;
import org.mapstruct.*;

import static org.springframework.boot.cloud.CloudPlatform.getActive;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserAccountMapper {
    UserAccount requestToEntity(UserRequest userRequest);

    UserRequest toDto(UserAccount userAccount);


    @Mapping(source = "active", target = "isActive")
    @Mapping(source = "createdOn", target = "createdOn", dateFormat = "dd-MM-yyyy HH:mm:ss")
    UserResponse entityToResponse(UserAccount userAccount);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserAccount partialUpdate(UserRequest userRequest, @MappingTarget UserAccount userAccount);
}