package com.customgrant.custom_grant.dtos;

import java.util.Set;
import java.util.stream.Collectors;

public record AccessLoginDTO(String userId, String email, Set<RoleDTO> roles) {

    public static AccessLoginDTO from(final String userId, final String email, final Set<String> roles) {

        return new AccessLoginDTO(
                userId,
                email,
                roles.stream().map(RoleDTO::of).collect(Collectors.toSet())
        );
    }
}
