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

    @Override
    public UserRole getUserRoleBySecurityRole(SecurityRole securityRole, Locale locale) throws Exception {
        LOGGER.info("Finding a user role by security role: {}", securityRole);
        UserRole userRole = userRoleRepository.findUserRoleBySecurityRole(securityRole);
        LOGGER.info("Found a user role: {}", userRole);
        return userRole;
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
        return createdUserRole;
    }

    /**
     * Check the user`s role in existence
     * @param userRole
     * @return true if exist otherwise false
     */
    private boolean isUserRoleExist(UserRole userRole) throws Exception{
        Query query = entityManager.createNativeQuery("SELECT * FROM hbschema.user_roles WHERE name = ?1", UserRole.class);
        query.setParameter(1, userRole.getSecurityRole().toString());
        try {
            UserRole existRole = (UserRole) query.getSingleResult();
            return  existRole != null;
        } catch (NoResultException e){
            return false;
        }
    }

    @Override
    public UserRole getById(Long id, Locale locale) throws Exception{
        LOGGER.info("Finding a user role by id: id={}", id);
        UserRole userRole = new UserRole();
        try {
            userRole = userRoleRepository.findOne(id);
            LOGGER.info("Found a user role: {}", userRole);
        } catch (Exception e) {
            LOGGER.error("Error while finding a user role by id: ", e);
            return null;
        }
        return userRole;
    }

    @Transactional
    @Override
    public UserRole update(UserRole entity, Locale locale) throws Exception{
        LOGGER.info("Updating a user role: {}", entity);
        UserRole userRole = new UserRole();
        try {
            userRole = userRoleRepository.findOne(entity.getId());
            userRole.update(entity);
            userRoleRepository.flush();
            LOGGER.info("Updated a user: {}", userRole);
        } catch (Exception e){
            LOGGER.error("Error while updating a user: ", e);
            return null;
        }
        return userRole;
    }

    @Override
    public void delete(UserRole entity, Locale locale) throws Exception{
        LOGGER.info("Deleting a user role: {}", entity);
        try {
            userRoleRepository.delete(entity);
            LOGGER.info("Deleted a user role: {}", entity);
        } catch (Exception e){
            LOGGER.error("Error while deleting a user role: ", e);
        }
    }
}
