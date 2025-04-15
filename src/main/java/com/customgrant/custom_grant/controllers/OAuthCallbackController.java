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

        final WebClient webClient = WebClient.create();

        return webClient.post()
                .uri("http://localhost:8080/oauth2/token")
                .headers(headers -> headers.setBasicAuth("default-client-id", "default-secret"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("code", code)
                        .with("redirect_uri", "http://localhost:8080/callback")
                        .with("client_id", "default-client-id")
                        .with("client_secret", "default-secret")
                        .with("code_verifier", "rCHJqeVpvtCX7pTOeZXc1RGNC8b-mhC0MOSPxvwXPNM")
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }
}
