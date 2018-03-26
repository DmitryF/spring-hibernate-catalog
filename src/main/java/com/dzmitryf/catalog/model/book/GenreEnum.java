package com.dzmitryf.catalog.model.book;

/**
 * Enumerating genres of books
 */
public enum GenreEnum {

    undefined,
    drama,
    comedy,
    tragedy;

    public static GenreEnum fromString(String value) {
        if (drama.toString().equals(value)) return drama;
        if (comedy.toString().equals(value)) return comedy;
        if (tragedy.toString().equals(value)) return tragedy;
        return undefined;
    }
}
