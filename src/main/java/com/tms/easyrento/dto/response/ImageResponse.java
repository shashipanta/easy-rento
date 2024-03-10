package com.tms.easyrento.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 3/1/24 3:47 PM
 */
@Getter
@Setter
public class ImageResponse {
    private Long id;
    private String fileName;
    private boolean featured;
    private String altText;
    private String imageType;
}
