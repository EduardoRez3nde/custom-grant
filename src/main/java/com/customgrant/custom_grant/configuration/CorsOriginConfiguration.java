package com.customgrant.custom_grant.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsOriginConfiguration {

    private List<String> allowedOrigin;
    private List<String> allowedMethods;
    private List<String> allowedHeaders;
    private boolean allowedCredentials;


    @Bean
    public CorsConfigurationSource corsConfiguration() {
        final CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin(allowedOrigin.toString());
        corsConfig.addAllowedMethod(allowedMethods.toString());
        corsConfig.addAllowedHeader(allowedHeaders.toString());
        corsConfig.setAllowCredentials(allowedCredentials);

        UrlBasedCorsConfigurationSource urlCorsSource = new UrlBasedCorsConfigurationSource();
        urlCorsSource.registerCorsConfiguration("/**", corsConfig);

        return urlCorsSource;
    }

    public List<String> getAllowedOrigin() {
        return allowedOrigin;
    }

    public void setAllowedOrigin(List<String> allowedOrigin) {
        this.allowedOrigin = allowedOrigin;
    }

    public List<String> getAllowedMethods() {
        return allowedMethods;
    }

    public void setAllowedMethods(List<String> allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public List<String> getAllowedHeaders() {
        return allowedHeaders;
    }

    public void setAllowedHeaders(List<String> allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    public boolean isAllowedCredentials() {
        return allowedCredentials;
    }

    public void setAllowedCredentials(boolean allowedCredentials) {
        this.allowedCredentials = allowedCredentials;
    }
}
