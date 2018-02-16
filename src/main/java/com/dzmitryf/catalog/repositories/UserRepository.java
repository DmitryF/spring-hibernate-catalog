package com.dzmitryf.catalog.repositories;

import com.dzmitryf.catalog.model.User;
import com.dzmitryf.catalog.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieve user by first name
     * @param firstName
     * @return the user with given first name or {@literal null} if none found
     */
    User findUserByFirstName(String firstName);

    /**
     * Retrivies users by count books
     * @return the users who sorted by max count of books
     */
    @Query(value = "SELECT * FROM hbschema.users\n" +
            "LEFT JOIN (\n" +
            "SELECT COUNT(user_id), user_id FROM hbschema.user_book \n" +
            "GROUP BY user_id \n" +
            "ORDER BY count DESC) u ON id = u.user_id", nativeQuery = true)
    List<User> findUsersByCountBooksDesc();
}
