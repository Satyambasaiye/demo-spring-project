package com.example.demo.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;

@Component
public class AuthApiClient {
    private static final Logger logger = LoggerFactory.getLogger(AuthApiClient.class);

    private final RestClient restClient;
    private final String loginUrl;
    private final String email;
    private final String password;

    public AuthApiClient(
            @Qualifier("authRestClient") RestClient restClient,
            @Value("${app.login.url}") String loginUrl,
            @Value("${app.user.email}") String email,
            @Value("${app.user.password}") String password) {
        this.restClient = restClient;
        this.loginUrl = loginUrl;
        this.email = "help@example.com";
        this.password = "password123";
    }

    public String getBearerToken() {
        System.out.println("Auth configuration: loginUrl=" + loginUrl + ", email=" + email + ", emailBlank=" + (email == null || email.isBlank()) +
                ", passwordBlank=" + (password == null || password.isBlank()) +
                ", emailLength=" + (email == null ? 0 : email.length()) +
                ", passwordLength=" + (password == null ? 0 : password.length()));
        logger.info(
                "Auth configuration: loginUrl={}, email={}, emailBlank={}, passwordBlank={}, emailLength={}, passwordLength={}",
                loginUrl,
                email,
                email == null || email.isBlank(),
                password == null || password.isBlank(),
                email == null ? 0 : email.length(),
                password == null ? 0 : password.length());

        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "External API credentials are not configured");
        }

        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);

        try {
            LoginResponse response = restClient.post()
                    .uri(loginUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(LoginResponse.class);

            if (response == null || response.getToken() == null || response.getToken().isBlank()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY, "Authentication service returned no token");
            }
            return response.getToken();
        } catch (RestClientException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY, "Could not obtain a token from the authentication service", exception);
        }
    }
}
