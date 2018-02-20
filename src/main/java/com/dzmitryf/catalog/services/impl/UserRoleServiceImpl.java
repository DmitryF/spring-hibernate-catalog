package com.dzmitryf.catalog.services.impl;

import com.dzmitryf.catalog.model.base.SecurityRole;
import com.dzmitryf.catalog.model.user.UserRole;
import com.dzmitryf.catalog.repositories.UserRoleRepository;
import com.dzmitryf.catalog.services.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public UserRole getUserRoleBySecurityRole(SecurityRole securityRole) {
        LOGGER.info("Finding a user role by security role: {}", securityRole);
        UserRole userRole = userRoleRepository.findUserRoleBySecurityRole(securityRole);
        LOGGER.info("Found a user role: {}", userRole);
        return userRole;
    }

    @Transactional
    @Override
    public UserRole create(UserRole entity) {
        LOGGER.info("Creating a new user role: {}", entity);
        UserRole userRole = new UserRole();
        userRole.update(entity);
        if (!isUserRoleExist(entity)){
            try {
                entityManager.persist(userRole);
                entityManager.flush();
                LOGGER.info("Created a new user role: {}", userRole);
            } catch (Exception e) {
                LOGGER.error("Error while creating a new user role: ", e);
            }
        } else {
            LOGGER.info("User role already exist: {}", entity);
        }
        return userRole;
    }

    /**
     * Check the user`s role in existence
     * @param userRole
     * @return true if exist otherwise false
     */
    private boolean isUserRoleExist(UserRole userRole){
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
    public UserRole getById(Long id) {
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
    public UserRole update(UserRole entity) {
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
    public void delete(UserRole entity) {
        LOGGER.info("Deleting a user role: {}", entity);
        try {
            userRoleRepository.delete(entity);
            LOGGER.info("Deleted a user role: {}", entity);
        } catch (Exception e){
            LOGGER.error("Error while deleting a user role: ", e);
        }
    }
}
