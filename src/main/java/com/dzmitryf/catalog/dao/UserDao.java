package com.dzmitryf.catalog.dao;

import com.dzmitryf.catalog.model.User;
import com.dzmitryf.catalog.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    User save(User user);

    User findUserByFirstName(String firstName);

    @Query(value = "SELECT * FROM hbschema.users\n" +
            "LEFT JOIN (\n" +
            "SELECT COUNT(user_id), user_id FROM hbschema.user_book \n" +
            "GROUP BY user_id \n" +
            "ORDER BY count DESC) u ON id = u.user_id", nativeQuery = true)
    List<User> getUsersByCountBooksDesc();
}
