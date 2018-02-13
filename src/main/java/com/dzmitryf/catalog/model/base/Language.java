package com.dzmitryf.catalog.model.base;

public enum Language {

    UNDEFINED ("undefined"),
    RU ("ru"),
    EN ("en");

    private String name;

    private Language(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Language fromString(String value) {
        if (RU.toString().equals(value)) return RU;
        if (EN.toString().equals(value)) return EN;
        return UNDEFINED;
    }
}
