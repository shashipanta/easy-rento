package com.tms.easyrento.service;

import com.tms.easyrento.dto.request.DynamicPricingRequest;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/30/24 4:23 AM
 */
public interface DynamicPricingService {

    boolean trainModel(String filePath);

    Long getDynamicPrice(DynamicPricingRequest request);
}
