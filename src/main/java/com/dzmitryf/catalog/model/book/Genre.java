package com.dzmitryf.catalog.model.book;

/**
 * Enumerating genres of books
 */
public enum Genre {

    UNDEFINED ("undefined"),
    DRAMA ("drama"),
    COMEDY ("comedy"),
    TRAGEDY ("tragedy");

    private String name;

    private Genre(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Genre fromString(String value) {
        if (DRAMA.toString().equals(value)) return DRAMA;
        if (COMEDY.toString().equals(value)) return COMEDY;
        if (TRAGEDY.toString().equals(value)) return TRAGEDY;
        return UNDEFINED;
    }
}
