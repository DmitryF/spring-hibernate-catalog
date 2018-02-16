package com.dzmitryf.catalog.services.impl;

import com.dzmitryf.catalog.repositories.UserRepository;
import com.dzmitryf.catalog.model.User;
import com.dzmitryf.catalog.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public User create(User entity) {
        LOGGER.info("Creating a new user: {}", entity);
        try {
            entityManager.persist(entity);
            entityManager.flush();
            LOGGER.info("Created a new user: {}", entity);
        } catch (Exception e){
            LOGGER.error("Error while creating a new user: ", e);
            return null;
        }
        return entity;
    }

    @Override
    public User getById(Long id) {
        LOGGER.info("Finding a user by id: id={}", id);
        User user = null;
        try {
            user = userRepository.findOne(id);
        } catch (Exception e) {
            LOGGER.error("Error while finding a user by id: ", e);
            return null;
        }
        LOGGER.info("Found a user : {}", user);
        return user;
    }

    @Transactional
    @Override
    public User update(User entity) {
        LOGGER.info("Updating a user: {}", entity);
        try {
            User user = userRepository.findOne(entity.getId());
            user.update(entity);
            userRepository.flush();
            LOGGER.info("Updated a user: {}", user);
        } catch (Exception e){
            LOGGER.error("Error while updating a user: ", e);
            return null;
        }
        return entity;
    }

    @Override
    public void delete(User entity) {
        LOGGER.info("Deleting a user: {}", entity);
        try {
            userRepository.delete(entity);
            LOGGER.info("Deleted a user: {}", entity);
        } catch (Exception e){
            LOGGER.error("Error while deleting a user: ", e);
        }
    }

    public User getUserByFirstName(String firstName) {
        LOGGER.info("Finding a user by first name: {}", firstName);
        User user = userRepository.findUserByFirstName(firstName);
        LOGGER.info("Found a user: {}", user);
        return user;
    }

    public List<User> getUsersByCountBooksDesc() {
        LOGGER.info("Finding user by count books");
        List<User> users = userRepository.findUsersByCountBooksDesc();
        LOGGER.info("Found {} users", users.size());
        return users;
    }

}
