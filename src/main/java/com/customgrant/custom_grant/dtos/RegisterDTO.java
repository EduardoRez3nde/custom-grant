package com.customgrant.custom_grant.dtos;

import com.customgrant.custom_grant.entities.User;

import java.util.Set;
import java.util.stream.Collectors;

public record RegisterDTO(String username, String password, Set<RoleDTO> roles) {

    public static RegisterDTO from(User user) {
        return new RegisterDTO(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(RoleDTO::fromEntity).collect(Collectors.toSet())
        );
    }
}
