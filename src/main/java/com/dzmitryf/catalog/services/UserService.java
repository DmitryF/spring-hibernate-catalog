package com.dzmitryf.catalog.services;

import com.dzmitryf.catalog.model.user.User;

import java.util.List;
import java.util.Set;

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
     * Retrieve user by username
     * @param username
     * @return the user with given username or {@literal null} if none found
     */
    User getUserByUsername(String username);

    /**
     * Retrieve all users
     * @return users
     */
    List<User> getAllUsers();

    /**
     * Retrieves users by count books
     * @return the users who sorted by max count of books
     */
    List<User> getUsersByCountBooksDesc();
}
