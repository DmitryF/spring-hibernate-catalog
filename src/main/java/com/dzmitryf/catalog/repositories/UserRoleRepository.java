package com.dzmitryf.catalog.repositories;

import com.dzmitryf.catalog.model.base.SecurityRole;
import com.dzmitryf.catalog.model.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    /**
     * Retrieve user role by security role
     * @param securityRole
     * @return the user role with given security role or {@literal null} if none found
     */
    UserRole findUserRoleBySecurityRole(SecurityRole securityRole);
}
