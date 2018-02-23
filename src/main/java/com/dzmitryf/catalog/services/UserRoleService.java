package com.dzmitryf.catalog.services;

import com.dzmitryf.catalog.model.base.SecurityRole;
import com.dzmitryf.catalog.model.user.UserRole;

import java.util.Locale;

/**
 * Interface for work with service of user roles.
 */
public interface UserRoleService extends CrudService<UserRole> {

    /**
     * Retrieve user role by security role
     *
     * @param securityRole
     * @param locale
     * @return the user role with given security role or {@literal null} if none found
     */
    UserRole getUserRoleBySecurityRole(SecurityRole securityRole, Locale locale);
}
