package com.dzmitryf.catalog.services;

import com.dzmitryf.catalog.model.book.Book;

import java.util.List;
import java.util.Locale;

/**
 * Interface for work with service of books.
 */
public interface BookService extends CrudService<Book> {

    /**
     * Retrieve book by name
     *
     * @param name book name
     * @param locale
     * @return the book with given name or {@literal null} if none found
     */
    Book getBookByName(String name, Locale locale);

    /**
     * Retrieves books with max count pages
     *
     * @param locale
     * @return the books that sorted by descending
     */
    List<Book> getBooksByCountPagesDesc(Locale locale);

    /**
     * Retrieves all books
     *
     * @param locale
     * @return books list
     */
    List<Book> getAllBooks(Locale locale);

    /**
     * Retrieve book by author name
     *
     * @param locale
     * @return the books with given author name
     */
    List<Book> getBooksByAuthorName(String authorName, Locale locale);
}
