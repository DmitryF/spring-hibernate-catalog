package com.dzmitryf.catalog.services;

import com.dzmitryf.catalog.model.book.Book;

import java.util.List;

/**
 * Interface for work with service of books.
 */
public interface BookService extends CrudService<Book> {

    /**
     * Retrieve book by name
     * @param name book name
     * @return the book with given name or {@literal null} if none found
     */
    Book getBookByName(String name);

    /**
     * Retrivies books with max count pages
     * @return the books that sorted by descending
     */
    List<Book> getBooksByCountPagesDesc();

    /**
     * Retrieve book by author name
     * @return the books with given author name
     */
    List<Book> getBooksByAuthorName(String authorName);
}
