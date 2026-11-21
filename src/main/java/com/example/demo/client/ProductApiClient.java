package com.example.demo.client;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.ProductResponse;

@Component
public class ProductApiClient {
    private static final Logger logger = LoggerFactory.getLogger(ProductApiClient.class);

    private final RestClient restClient;
    private final String path;

    public ProductApiClient(
            @Qualifier("productRestClient") RestClient restClient,
            @Value("${app.services.product.path}") String path) {
        this.restClient = restClient;
        this.path = path;
    }

    public ProductResponse getProduct(UUID productId) {
        try {
            logger.debug("Sending product request: GET {}?productId={}", path, productId);
            ProductResponse product = restClient.get()
                    .uri(uriBuilder -> uriBuilder.path(path)
                            .queryParam("productId", productId)
                            .build())
                    .retrieve()
                    .body(ProductResponse.class);
                            logger.info("Raw Product API Response: {}", product);


            if (product == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY, "Product service returned an empty response");
            }
            logger.debug("Received product response: {}", product);
            return product;
        } catch (HttpClientErrorException.NotFound exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product not found: " + productId, exception);
        } catch (RestClientException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY, "Could not retrieve product from product service", exception);
        }
    }
}
