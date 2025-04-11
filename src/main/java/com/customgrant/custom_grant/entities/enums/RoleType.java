package com.customgrant.custom_grant.entities.enums;

public enum RoleType {

    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    final String type;

    RoleType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
