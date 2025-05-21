package com.customgrant.custom_grant.dtos;

import com.customgrant.custom_grant.entities.Role;
import com.customgrant.custom_grant.entities.enums.RoleType;

public record RoleDTO(String authority) {

    public static RoleDTO fromEntity(Role role) {
        return new RoleDTO(role.getAuthority());
    }

    public static RoleDTO of(final String authority) {
        return new RoleDTO(authority);
    }

    public RoleType toRoleType() { return RoleType.valueOf(authority); }
}
