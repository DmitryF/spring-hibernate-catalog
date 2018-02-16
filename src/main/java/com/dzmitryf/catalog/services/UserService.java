package com.dzmitryf.catalog.services;

import com.dzmitryf.catalog.model.User;
import com.dzmitryf.catalog.model.book.Book;

import java.util.List;

/**
 * Interface for work with service of books.
 */
public interface UserService extends CrudService<User> {

    /**
     * Retrieve user by first name
     * @param firstName
     * @return the user with given first name or {@literal null} if none found
     */
    User getUserByFirstName(String firstName);

    /**
     * Retrieves users by count books
     * @return the users who sorted by max count of books
     */
    List<User> getUsersByCountBooksDesc();
}
