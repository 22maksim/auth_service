package com.home.auth_service.model.enums;

import lombok.Getter;

@Getter
public enum Permission {
    WRITE("permission:write"),
    READ("permission:read"),
    UPDATE("permission:update"),
    DELETE("permission:delete");
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
