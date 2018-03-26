package com.dzmitryf.catalog.services;

import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.model.book.Genre;
import com.dzmitryf.catalog.model.book.GenreEnum;

import java.util.List;
import java.util.Locale;

/**
 * Interface for work with service of books.
 */
public interface BookService extends CrudService<Book> {

    /**
     * Retrieve book by name
     * @param name book name
     * @param locale
     * @return the book with given name or {@literal null} if none found
     * @throws Exception
     */
    Book getBookByName(String name, Locale locale) throws Exception;

    /**
     * Retrieves books with max count pages
     * @param locale
     * @return the books that sorted by descending
     * @throws Exception
     */
    List<Book> getBooksByCountPagesDesc(Locale locale) throws Exception;

    /**
     * Retrieves all books
     * @param locale
     * @return books list
     * @throws Exception
     */
    List<Book> getAllBooks(Locale locale) throws Exception;

    /**
     * Retrieve books by genre
     * @param genre
     * @param locale
     * @return the books with given genre
     * @throws Exception
     */
    List<Book> getBookByGenre(Genre genre, Locale locale) throws Exception;
}
