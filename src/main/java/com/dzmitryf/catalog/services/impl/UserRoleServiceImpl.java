package com.dzmitryf.catalog.services.impl;

import com.dzmitryf.catalog.model.base.SecurityRole;
import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.model.user.UserRole;
import com.dzmitryf.catalog.repositories.UserRoleRepository;
import com.dzmitryf.catalog.services.UserRoleService;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Locale;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     * Retrieve user role by security role
     * @param securityRole
     * @param locale
     * @return the user role with given security role or {@literal null} if none found
     * @throws Exception
     * @throws ApiServiceException if user role not found
     */
    @Override
    public UserRole getUserRoleBySecurityRole(SecurityRole securityRole, Locale locale) throws Exception {
        LOGGER.info(messageSource.getMessage("user.role.service.get.user.role.by.security.role", new Object[]{securityRole}, locale));
        try {
            if (securityRole == null){
                throw new IllegalArgumentException();
            }
            UserRole userRole = userRoleRepository.findUserRoleBySecurityRole(securityRole);
            if (userRole == null) {
                throw new IllegalArgumentException();
            }
            LOGGER.info(messageSource.getMessage("user.service.found.user", new Object[]{userRole}, locale));
            return userRole;
        } catch (IllegalArgumentException e){
            LOGGER.info(messageSource.getMessage("user.role.service.user.role.security.role.not.found", new Object[]{securityRole}, locale));
            throw new ApiServiceException(messageSource.getMessage("user.role.service.user.role.security.role.not.found", new Object[]{securityRole}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
    }

    /**
     * Create a given user role. Use the returned instance for further operations as the save operation might have changed the
     * user role instance completely.
     * @param userRole role must note be {@literal null}
     * @param locale
     * @return the saved user role
     * @throws Exception
     * @throws ApiServiceException if user role not entity or already exists
     */
    @Transactional(rollbackOn = ApiServiceException.class)
    @Override
    public UserRole create(UserRole userRole, Locale locale) throws Exception {
        LOGGER.info(messageSource.getMessage("user.role.service.create.user.role", new Object[]{userRole}, locale));
        UserRole createdUserRole = new UserRole();
        try {
            createdUserRole.update(userRole);
            entityManager.persist(createdUserRole);
            entityManager.flush();
            LOGGER.info(messageSource.getMessage("user.role.service.created.user.role", new Object[]{createdUserRole}, locale));
            return createdUserRole;
        } catch (EntityExistsException e) {
            LOGGER.info(messageSource.getMessage("user.role.service.user.role.already.exist",
                    new Object[]{userRole.getSecurityRole().toString()}, locale));
            throw new ApiServiceException(messageSource.getMessage("user.role.service.user.role.already.exist",
                    new Object[]{userRole.getSecurityRole().toString()}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        } catch (PersistenceException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                LOGGER.info(messageSource.getMessage("user.role.service.user.role.is.unique",
                        new Object[]{userRole.getSecurityRole().toString()}, locale));
                throw new ApiServiceException(messageSource.getMessage("user.role.service.user.role.is.unique",
                        new Object[]{userRole.getSecurityRole().toString()}, locale),
                        HttpStatus.PRECONDITION_FAILED);
            } else {
                throw e;
            }
        } catch (IllegalArgumentException e){
            LOGGER.info(messageSource.getMessage("user.role.service.user.role.not.entity", new Object[]{userRole.getId()}, locale));
            throw new ApiServiceException(messageSource.getMessage("user.service.user.not.entity", new Object[]{userRole.getId()}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("user.role.service.error.create.user.role", null, locale), e);
            throw e;
        }
    }

    /**
     * Retrieves an user role by its id.
     * @param id must not be {@literal null}.
     * @param locale
     * @return the user role with the given id or {@literal null} if none found
     * @throws Exception
     * @throws ApiServiceException if user role not found
     */
    @Override
    public UserRole getById(Long id, Locale locale) throws Exception{
        LOGGER.info(messageSource.getMessage("user.role.service.get.user.role.by.id", new Object[]{id}, locale));
        UserRole userRole = new UserRole();
        try {
            userRole = userRoleRepository.findOne(id);
            if (userRole == null){
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            LOGGER.info(messageSource.getMessage("user.role.service.user.role.id.not.found", new Object[]{id}, locale));
            throw new ApiServiceException(messageSource.getMessage("user.role.service.user.role.id.not.found", new Object[]{id}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
        LOGGER.info(messageSource.getMessage("user.role.service.found.user.role", new Object[]{userRole}, locale));
        return userRole;
    }

    /**
     * Update a given user role. Use the returned instance for further operations as the save operation might have changed the
     * user role instance completely.
     * @param userRole must note be {@literal null}
     * @param locale
     * @return the updated user
     * @throws Exception
     * @throws ApiServiceException if user not found
     */
    @Transactional
    @Override
    public UserRole update(UserRole userRole, Locale locale) throws Exception{
        LOGGER.info(messageSource.getMessage("user.role.service.update.user.role", new Object[]{userRole}, locale));
        UserRole updatedUserRole = new UserRole();
        try {
            if (userRole == null) {
                throw new IllegalArgumentException();
            }
            updatedUserRole = userRoleRepository.findOne(userRole.getId());
            updatedUserRole.update(userRole);
            userRoleRepository.flush();
            LOGGER.info(messageSource.getMessage("user.role.service.updated.user.role", new Object[]{updatedUserRole}, locale));
        } catch (IllegalArgumentException e) {
            LOGGER.info(messageSource.getMessage("user.role.service.user.role.id.not.found", new Object[]{0}, locale));
            throw new ApiServiceException(messageSource.getMessage("user.role.service.user.role.id.not.found", new Object[]{0}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
        return updatedUserRole;
    }

    /**
     * Deletes a given user role.
     * @param userRole must note be {@literal null}
     * @param locale
     * @throws Exception
     * @throws ApiServiceException if user role not found
     */
    @Override
    public void delete(UserRole userRole, Locale locale) throws Exception{
        LOGGER.info(messageSource.getMessage("user.role.service.delete.user.role", new Object[]{userRole}, locale));
        try {
            userRoleRepository.delete(userRole);
            LOGGER.info(messageSource.getMessage("user.role.service.deleted.user.role", new Object[]{userRole}, locale));
        } catch (IllegalArgumentException e) {
            LOGGER.info(messageSource.getMessage("user.role.service.user.role.id.not.found", new Object[]{0}, locale));
            throw new ApiServiceException(messageSource.getMessage("user.role.service.user.role.id.not.found", new Object[]{0}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
    }
}
