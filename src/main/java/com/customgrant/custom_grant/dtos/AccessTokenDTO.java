package com.customgrant.custom_grant.dtos;

public record AccessTokenDTO(String userId, String token) {

    public static AccessTokenDTO from(final String userId, final String token) {
        return new AccessTokenDTO(userId, token);
    }
}
