package com.customgrant.custom_grant.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class OAuthCallbackController {

    @GetMapping("/callback")
    public ResponseEntity<?> callbackExample(
            @RequestParam(value = "code") String code
    ) {
        System.out.println("Code " + code);

        final String accessTokenResponse = this.codePerToken(code);

        return ResponseEntity.ok(accessTokenResponse);
    }

    public String codePerToken(String code) {

        final WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .defaultHeaders(headers -> headers.setBasicAuth("default-client-id", "default-secret"))
                .build();

        return webClient.post()
                .uri("/oauth2/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", "default-client-id")
                        .with("code", code)
                        .with("redirect_uri", "http://localhost:8080/callback")
                        .with("code_verifier", "e-ZUM9ycJVDA7icWxAwa-5CckCZMSka9tUZmLiUTRk4")
                )
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class).map(RuntimeException::new))
                .bodyToMono(String.class)
                .block();
    }
}