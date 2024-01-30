package com.tms.easyrento.config.webClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/30/24 4:20 AM
 */
@Configuration
public class WebClientConfig {

    private static final String BASE_URL = "http://127.0.0.1:5000/";
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .baseUrl(BASE_URL)
                .filter(logRequest())
                .filter(logResponse());
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            System.out.println("Request: " + clientRequest.method() + " " + clientRequest.url());
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            System.out.println("Response: " + clientResponse.statusCode());
            return Mono.just(clientResponse);
        });
    }
}
