package com.dzmitryf.catalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("username1").password("1234")
                .authorities("ADMIN")
                .and().withUser("admin").password("adminPass")
                .authorities("ADMIN");

        //auth.userDetailsService(userDetailsService);
       /* auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "SELECT hbschema.users.id, hbschema.users.user_name, hbschema.users.password FROM hbschema.users where hbschema.users.user_name=?")
                .authoritiesByUsernameQuery(
                        "SELECT hbschema.user_roles.id, hbschema.user_roles.name FROM  hbschema.user_roles JOIN hbschema.users \n" +
                                " ON (hbschema.users.user_role_id = hbschema.user_roles.id AND hbschema.users.user_name =?)");*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").hasRole("ADMIN");
        ;
    }
}
