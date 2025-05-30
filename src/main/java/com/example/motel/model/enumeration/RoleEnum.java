package com.example.motel.model.enumeration;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ACCOUNT_ADMIN("ROLE_ADMIN"),
    ACCOUNT_USER("ROLE_USER");

    private final String role;
    RoleEnum(String roleAdmin) {
        this.role = roleAdmin;
    }
}