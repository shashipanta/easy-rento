package com.tms.easyrento.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tms.easyrento.dto.request.DynamicPricingRequest;
import com.tms.easyrento.service.DynamicPricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/30/24 4:30 AM
 */

@Service
public class DynamicPricingServiceImpl implements DynamicPricingService {

    private final WebClient webClient;

    @Autowired
    public DynamicPricingServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://127.0.0.1:5000").build();
    }

    @Override
    public boolean trainModel(String filePath) {
        return false;
    }

    @Override
    public Long getDynamicPrice(DynamicPricingRequest request) {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("features", request);
        PredictionResponse predictionResponse = webClient.post()
                .uri("/predict_price")
                .header("Content-Type", "application/json")
                .body(BodyInserters.fromValue(requestData))
                .retrieve()
                .bodyToMono(PredictionResponse.class)
                .timeout(Duration.ofSeconds(10))  // Set the timeout here
                .onErrorResume(TimeoutException.class, ex -> Mono.empty().ofType(PredictionResponse.class))  // Handle timeout
                .block();

//        System.out.println("Dynamic Price :"+ predictionResponse.getPredictedPrice());

        if(predictionResponse != null)
            return predictionResponse.getPredictedPrice() * 10L;
        else
            return 0L;

    }

    public static class PredictionResponse {
        @JsonProperty("predicted_price")
        private Long predictedPrice;

        public Long getPredictedPrice() {
            return predictedPrice;
        }

        public void setPredictedPrice(Long predictedPrice) {
            this.predictedPrice = predictedPrice;

        }

    }
}
