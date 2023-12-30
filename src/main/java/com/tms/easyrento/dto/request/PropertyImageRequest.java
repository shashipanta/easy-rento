package com.tms.easyrento.dto.request;

import com.tms.easyrento.constants.FieldErrorConstants;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/23/23 5:51 AM
 */

@Getter
@Setter
public class PropertyImageRequest {

    private Long id;

    @NotNull(message = FieldErrorConstants.NOT_NULL)
    private MultipartFile file;

    private String fileName;


}
