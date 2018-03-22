package com.dzmitryf.catalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Collection;

/**
 * Application security configuration
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    /**
     * Configure security for requests
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/user/*").hasRole("ADMIN")
                .antMatchers("/api/comment/**").hasRole("ADMIN")
                .antMatchers("/api/login").permitAll()
                .and()
                .formLogin().successHandler(getLoginAuthenticationSuccessHandler())
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }

    /**
     * Get {@link AuthenticationSuccessHandler} for login request
     * @return {@link AuthenticationSuccessHandler}
     */
    private AuthenticationSuccessHandler getLoginAuthenticationSuccessHandler(){
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                User user = (User) authentication.getPrincipal();
                if (user != null) {
                    httpServletRequest.setAttribute("username", user.getUsername());
                }
                httpServletRequest.getRequestDispatcher("/api/login").forward(httpServletRequest, httpServletResponse);
            }
        };
    }

    /**
     * Get bean {@link UserDetailsService} through user authentication in database
     * @return userDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService(){
        JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
        jdbcDao.setDataSource(dataSource);
        jdbcDao.setUsersByUsernameQuery("SELECT hbschema.users.user_name, hbschema.users.password, hbschema.users.enabled FROM hbschema.users where hbschema.users.user_name=?");
        jdbcDao.setAuthoritiesByUsernameQuery("SELECT hbschema.user_roles.id, hbschema.user_roles.name FROM  hbschema.user_roles JOIN hbschema.users \n" +
                " ON (hbschema.users.user_role_id = hbschema.user_roles.id AND hbschema.users.user_name =?)");

        return jdbcDao;
    }

    /**
     * Get simple password encoder
     * @return password encoder without encoding
     */
    private PasswordEncoder getPasswordEncoder(){
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return true;
            }
        };
    }
}
