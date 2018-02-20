package com.dzmitryf.catalog.model.user;

import com.dzmitryf.catalog.model.base.BaseEntity;
import com.dzmitryf.catalog.model.base.SecurityRole;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name="USER_ROLES", schema="hbschema")
public class UserRole extends BaseEntity {

    private SecurityRole securityRole = SecurityRole.ROLE_GUEST;

    private User user;

    public UserRole(){}

    public UserRole(SecurityRole securityRole){
        setSecurityRole(securityRole);
    }

    @Enumerated(EnumType.STRING)
    @Column(name="NAME", unique=true, nullable=false)
    public SecurityRole getSecurityRole() {
        return securityRole;
    }

    public void setSecurityRole(SecurityRole securityRole) {
        this.securityRole = securityRole;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
