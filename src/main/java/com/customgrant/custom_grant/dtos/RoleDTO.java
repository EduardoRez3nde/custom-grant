package com.customgrant.custom_grant.dtos;

import com.customgrant.custom_grant.entities.Role;
import com.customgrant.custom_grant.entities.enums.RoleType;

public record RoleDTO(String authority) {

    public static RoleDTO fromEntity(Role role) {
        return new RoleDTO(role.getAuthority());
    }

    public RoleType toRoleType() { return RoleType.valueOf(authority); }
}
