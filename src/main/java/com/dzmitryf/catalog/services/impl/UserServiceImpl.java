package com.dzmitryf.catalog.services.impl;

import com.dzmitryf.catalog.repositories.UserRepository;
import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.services.UserRoleService;
import com.dzmitryf.catalog.services.UserService;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     * Create a given user. Use the returned instance for further operations as the save operation might have changed the
     * user instance completely.
     * @param user must note be {@literal null}
     * @param locale
     * @return the saved user
     * @throws Exception
     * @throws ApiServiceException if user not entity or already exists
     */
    @Transactional
    @Override
    public User create(User user, Locale locale) throws Exception {
        LOGGER.info(messageSource.getMessage("user.service.create.user", new Object[]{user}, locale));
        User createdUser = new User();
        try {
            createdUser.update(user);
            entityManager.persist(createdUser);
            entityManager.flush();
            LOGGER.info(messageSource.getMessage("user.service.created.user", new Object[]{createdUser}, locale));
        } catch (EntityExistsException e) {
            LOGGER.info(messageSource.getMessage("user.service.user.already.exist", new Object[]{user}, locale));
            throw new ApiServiceException(messageSource.getMessage("user.service.user.already.exist", new Object[]{user}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        } catch (IllegalArgumentException e){
            LOGGER.info(messageSource.getMessage("user.service.user.not.entity", new Object[]{user}, locale));
            throw new ApiServiceException(messageSource.getMessage("user.service.user.not.entity", new Object[]{user}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("user.service.error.create.user", null, locale), e);
            throw e;
        }
        return createdUser;
    }

    /**
     * Retrieves an user by its id.
     * @param id must not be {@literal null}.
     * @param locale
     * @return the user with the given id or {@literal null} if none found
     * @throws Exception
     * @throws ApiServiceException if user not found
     */
    @Transactional
    @Override
    public User getById(Long id, Locale locale) throws Exception {
        LOGGER.info(messageSource.getMessage("user.service.get.user.by.id", new Object[]{id}, locale));
        User user = new User();
        try {
            user = userRepository.findOne(id);
            if (user == null){
                throw new IllegalArgumentException();
            }
            loadUserRole(user, locale);
        } catch (IllegalArgumentException e) {
            LOGGER.info(messageSource.getMessage("user.service.user.id.not.found", new Object[]{id}, locale));
            throw new ApiServiceException(messageSource.getMessage("user.service.user.id.not.found", new Object[]{id}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
        LOGGER.info(messageSource.getMessage("user.service.found.user", new Object[]{user}, locale));
        return user;
    }

    /**
     * Update a given user. Use the returned instance for further operations as the save operation might have changed the
     * user instance completely.
     * @param user must note be {@literal null}
     * @param locale
     * @return the updated user
     * @throws Exception
     * @throws ApiServiceException if user not found
     */
    @Transactional
    @Override
    public User update(User user, Locale locale) throws Exception {
        LOGGER.info(messageSource.getMessage("user.service.update.user", new Object[]{user}, locale), user);
        User updatedUser = new User();
        try {
            if (user == null) {
                throw new IllegalArgumentException();
            }
            updatedUser = userRepository.findOne(user.getId());
            updatedUser.update(user);
            userRepository.flush();
            LOGGER.info(messageSource.getMessage("user.service.updated.user", new Object[]{updatedUser}, locale));
        } catch (IllegalArgumentException e) {
            LOGGER.info(messageSource.getMessage("user.service.user.id.not.found", new Object[]{0}, locale));
            throw new ApiServiceException(messageSource.getMessage("user.service.user.id.not.found", new Object[]{0}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
        return updatedUser;
    }

    /**
     * Deletes a given user.
     * @param user must note be {@literal null}
     * @param locale
     * @throws Exception
     * @throws ApiServiceException if user not found
     */
    @Override
    public void delete(User user, Locale locale) throws Exception {
        LOGGER.info(messageSource.getMessage("user.service.delete.user", new Object[]{user}, locale));
        try {
            if (user == null) {
                throw new IllegalArgumentException();
            }
            userRepository.delete(user);
            LOGGER.info(messageSource.getMessage("user.service.deleted.user", new Object[]{user}, locale));
        } catch (IllegalArgumentException e) {
            LOGGER.info(messageSource.getMessage("user.service.user.id.not.found", new Object[]{0}, locale));
            throw new ApiServiceException(messageSource.getMessage("user.service.user.id.not.found", new Object[]{0}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
    }

    /**
     * Retrieve user by first name
     * @param firstName
     * @param locale
     * @return the user with given first name or {@literal null} if none found
     * @throws Exception
     * @throws ApiServiceException if user not found
     */
    @Transactional
    @Override
    public User getUserByFirstName(String firstName, Locale locale) throws Exception {
        LOGGER.info(messageSource.getMessage("user.service.get.user.by.first.name", new Object[]{firstName}, locale));
        User user = userRepository.findUserByFirstName(firstName);
        if (user == null) {
            LOGGER.info(messageSource.getMessage("user.service.user.first.name.not.found", new Object[]{firstName}, locale));
            throw new ApiServiceException(messageSource.getMessage("user.service.user.first.name.not.found", new Object[]{firstName}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
        try {
            loadUserRole(user, locale);
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("user.service.error.get.user.by.first.name", new Object[]{firstName}, locale), e);
            throw e;
        }
        LOGGER.info(messageSource.getMessage("user.service.found.user", new Object[]{user}, locale));
        return user;
    }

    /**
     * Retrieve user by username
     * @param username
     * @param locale
     * @return the user with given username or {@literal ApiServiceException} if none found
     * @throws Exception
     * @throws ApiServiceException if user not found
     */
    @Transactional
    @Override
    public User getUserByUsername(String username, Locale locale) throws Exception {
        LOGGER.info(messageSource.getMessage("user.service.get.user.by.username", new Object[]{username}, locale));
        User user = userRepository.findUserByUserName(username);
        if (user == null) {
            LOGGER.info(messageSource.getMessage("user.service.user.username.not.found", new Object[]{username}, locale));
            throw new ApiServiceException(messageSource.getMessage("user.service.user.username.not.found", new Object[]{username}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
        try {
            loadUserRole(user, locale);
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("user.service.error.get.user.by.username", new Object[]{username}, locale), e);
            throw e;
        }
        LOGGER.info(messageSource.getMessage("user.service.found.user", new Object[]{user}, locale));
        return user;
    }

    /**
     * Retrieves users by count books
     * @param locale
     * @return the users who sorted by max count of books
     * @throws Exception
     */
    @Transactional
    @Override
    public List<User> getUsersByCountBooksDesc(Locale locale) throws Exception {
        LOGGER.info(messageSource.getMessage("user.service.get.user.by.count.books", null, locale));
        List<User> users = userRepository.findUsersByCountBooksDesc();
        try {
            for (User user : users) {
                loadUserRole(user, locale);
            }
        } catch (Exception e){
            LOGGER.error(messageSource.getMessage("user.service.error.get.user.by.count.books", null, locale), e);
            throw e;
        }
        LOGGER.info(messageSource.getMessage("user.service.found.users", new Object[]{users.size()}, locale));
        return users;
    }

    /**
     * Retrieve all users
     * @param locale
     * @return users list
     * @throws Exception
     */
    @Transactional
    @Override
    public List<User> getAllUsers(Locale locale) throws Exception {
        LOGGER.info(messageSource.getMessage("user.service.get.user.all", null, locale));
        List<User> users = userRepository.findAll();
        try {
            for (User user : users) {
                loadUserRole(user, locale);
            }
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("user.service.error.get.user.all", null, locale), e);
            throw e;
        }
        LOGGER.info(messageSource.getMessage("user.service.found.users", new Object[]{users.size()}, locale));
        return users;
    }

    /**
     * Load role for user
     * @param locale
     * @param user
     * @throws Exception
     */
    @Transactional
    private void loadUserRole(User user, Locale locale) throws Exception {
        user.getUserRole().getSecurityRole();
    }
}
