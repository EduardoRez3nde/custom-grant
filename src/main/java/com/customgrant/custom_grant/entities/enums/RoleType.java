package com.customgrant.custom_grant.entities.enums;

public enum RoleType {

    ROLE_TEACHER("TEACHER"),
    ROLE_STUDENT("STUDENT");

    final String type;

    RoleType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
