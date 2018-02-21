package com.dzmitryf.catalog.model.user;

import com.dzmitryf.catalog.model.base.BaseEntity;
import com.dzmitryf.catalog.model.base.SecurityRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name="USER_ROLES", schema="hbschema")
public class UserRole extends BaseEntity {

    private SecurityRole securityRole = SecurityRole.ROLE_GUEST;

    @JsonIgnore
    private Set<User> users;

    public UserRole(){}

    public UserRole(SecurityRole securityRole){
        setSecurityRole(securityRole);
    }

    public void update(UserRole entity) {
        super.update(entity);
        if (entity != null){
            setSecurityRole(entity.getSecurityRole());
            setUsers(entity.getUsers());
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(name="NAME", unique=true, nullable=false)
    public SecurityRole getSecurityRole() {
        return securityRole;
    }

    public void setSecurityRole(SecurityRole securityRole) {
        this.securityRole = securityRole;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userRole")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @JsonIgnore
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public String toString() {
        return "UserRole [id=" + getId() +
                ", securityRole=" + getSecurityRole().toString() +
                "]";
    }
}
