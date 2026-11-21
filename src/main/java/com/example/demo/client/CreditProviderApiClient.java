package com.example.demo.client;

import java.util.List;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.CreditProviderResponse;

@Component
public class CreditProviderApiClient {
    private static final Logger logger = LoggerFactory.getLogger(CreditProviderApiClient.class);

    private final RestClient restClient;
    private final String path;

    public CreditProviderApiClient(
            @Qualifier("creditProviderRestClient") RestClient restClient,
            @Value("${app.services.credit-providers.path}") String path) {
        this.restClient = restClient;
        this.path = path;
    }

    public List<CreditProviderResponse> getProviders(BigDecimal amount) {
        try {
            logger.debug("Sending credit provider request: GET {}?amount={}", path, amount);
            System.out.println("Sending credit provider request: GET " + path + "?amount=" + amount);
            List<CreditProviderResponse> providers = restClient.get()
                    .uri(uriBuilder -> uriBuilder.path(path)
                            .queryParam("amount", amount)
                            .build())
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            if (providers == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY, "Credit provider service returned an empty response");
            }
            logger.debug("Received credit provider response: {}", providers);
            return providers;
        } catch (RestClientException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY, "Could not retrieve credit providers", exception);
        }
    }
}
