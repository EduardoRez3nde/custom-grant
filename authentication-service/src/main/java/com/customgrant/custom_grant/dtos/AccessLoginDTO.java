package com.customgrant.custom_grant.dtos;

public record AccessLoginDTO(String username, RoleDTO role) {

    public static AccessLoginDTO from(final String username, final String role) {

        return new AccessLoginDTO(
                username,
                RoleDTO.of(role)
        );
    }
}
