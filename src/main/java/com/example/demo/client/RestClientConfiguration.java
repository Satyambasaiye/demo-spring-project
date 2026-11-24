package com.example.demo.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {
    @Bean
    @Qualifier("authRestClient")
    RestClient authRestClient() {
        return RestClient.builder().build();
    }

    @Bean
    @Qualifier("productRestClient")
    RestClient productRestClient(
            @Value("${app.services.product.base-url}") String baseUrl) {
        return RestClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    @Qualifier("creditProviderRestClient")
    RestClient creditProviderRestClient(
            @Value("${app.services.credit-providers.base-url}") String baseUrl) {
        return RestClient.builder().baseUrl(baseUrl).build();
    }
}
