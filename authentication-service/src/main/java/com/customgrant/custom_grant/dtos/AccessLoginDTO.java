package com.customgrant.custom_grant.dtos;

import com.customgrant.custom_grant.entities.Role;

public record AccessLoginDTO(String username, RoleDTO role) {

    public static AccessLoginDTO from(final String username, final String role) {

        return new AccessLoginDTO(
                username,
                RoleDTO.of(role)
        );
    }
}
