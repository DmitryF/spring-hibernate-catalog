package com.dzmitryf.catalog.services;

import com.dzmitryf.catalog.model.User;
import com.dzmitryf.catalog.model.book.Book;

import java.util.List;

public interface UserService {

    User save(User user);

    User findUserByFirstName(String firstName);

    List<User> getUsersByCountBooksDesc();
}
