package com.dzmitryf.catalog.services;

import com.dzmitryf.catalog.model.book.Genre;

import java.util.List;
import java.util.Locale;

/**
 * Interface for work with service of books.
 */
public interface GenreService extends CrudService<Genre> {

    /**
     * Retrieve genre by name
     * @param name genre name
     * @param locale
     * @return the genre with given name or {@literal null} if none found
     * @throws Exception
     */
    Genre getGenreByName(String name, Locale locale) throws Exception;

    /**
     * Retrieves all genres of books
     * @param locale
     * @return genres list
     * @throws Exception
     */
    List<Genre> getAllGenres(Locale locale) throws Exception;
}
