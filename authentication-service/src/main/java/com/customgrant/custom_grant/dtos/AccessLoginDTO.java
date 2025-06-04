package com.customgrant.custom_grant.dtos;

public record AccessLoginDTO(String id, String username, RoleDTO role) {

    public static AccessLoginDTO from(final String id, final String username, final String role) {

        return new AccessLoginDTO(
                id,
                username,
                RoleDTO.of(role)
        );
    }
}
