package com.dzmitryf.catalog.model.base;

public enum SecurityRole {

    ROLE_GUEST ("ROLE_GUEST"),
    ROLE_USER ("ROLE_USER"),
    ROLE_ADMIN ("ROLE_ADMIN");

    private String name;

    private SecurityRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static SecurityRole fromString(String value) {
        if (ROLE_GUEST.toString().equals(value)) return ROLE_GUEST;
        if (ROLE_USER.toString().equals(value)) return ROLE_USER;
        if (ROLE_ADMIN.toString().equals(value)) return ROLE_ADMIN;
        return ROLE_GUEST;
    }
}
