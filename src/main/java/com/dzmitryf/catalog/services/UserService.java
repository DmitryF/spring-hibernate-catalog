package com.dzmitryf.catalog.services;

import com.dzmitryf.catalog.model.user.User;

import java.util.List;
import java.util.Locale;

/**
 * Interface for work with service of books.
 */
public interface UserService extends CrudService<User> {

    /**
     * Retrieve user by first name
     * @param firstName
     * @param locale
     * @return the user with given first name or {@literal null} if none found
     * @throws Exception
     */
    User getUserByFirstName(String firstName, Locale locale) throws Exception;

    /**
     * Retrieve user by username
     * @param username
     * @param locale
     * @return the user with given username or {@literal null} if none found
     * @throws Exception
     */
    User getUserByUsername(String username, Locale locale) throws Exception;

    /**
     * Retrieve all users
     * @param locale
     * @return users
     * @throws Exception
     */
    List<User> getAllUsers(Locale locale) throws Exception;

    /**
     * Retrieves users by count books
     * @param locale
     * @return the users who sorted by max count of books
     * @throws Exception
     */
    List<User> getUsersByCountBooksDesc(Locale locale) throws Exception;
}
