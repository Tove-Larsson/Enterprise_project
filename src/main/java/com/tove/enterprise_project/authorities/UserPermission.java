package com.tove.enterprise_project.authorities;

public enum UserPermission {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }
    public String getPermission() {
        return permission;
    }
}
