package com.dzmitryf.catalog.services.impl;

import com.dzmitryf.catalog.dao.UserDao;
import com.dzmitryf.catalog.model.User;
import com.dzmitryf.catalog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    public User findUserByFirstName(String firstName) {
        return userDao.findUserByFirstName(firstName);
    }

    public List<User> getUsersByCountBooksDesc() {
        return userDao.getUsersByCountBooksDesc();
    }
}
