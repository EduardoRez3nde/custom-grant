package com.customgrant.custom_grant.dtos;

import com.customgrant.custom_grant.entities.User;

import java.util.Set;
import java.util.stream.Collectors;

public record RegisterDTO(String username, String password, RoleDTO role) {

    public static RegisterDTO from(User user) {
        return new RegisterDTO(
                user.getUsername(),
                user.getPassword(),
                RoleDTO.fromEntity(user.getRole())
        );
    }
}
