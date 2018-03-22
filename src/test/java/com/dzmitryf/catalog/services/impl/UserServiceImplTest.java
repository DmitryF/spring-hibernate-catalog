package com.dzmitryf.catalog.services.impl;

import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.repositories.UserRepository;
import com.dzmitryf.catalog.services.UserService;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableJpaRepositories
@ContextConfiguration(classes = {UserServiceImplTest.UserServiceImplTestContextConfiguration.class})
public class UserServiceImplTest {

    @Configuration
    static class UserServiceImplTestContextConfiguration{

        @Bean
        public UserService userService(){
            return (UserService) new UserServiceImpl();
        }

        @Bean
        public UserRepository userRepository(){
            return mock(UserRepository.class);
        }

        @Bean
        public MessageSource messageSource(){
            return mock(MessageSource.class);
        }

        @Bean
        public EntityManager entityManager(){
            return mock(EntityManager.class);
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getById() throws Exception {
        User user = new User("user1", "password1");
        when(userRepository.findUserByUserName(user.getUserName())).thenReturn(user);

        User found = userService.getUserByUsername(user.getUserName(), Locale.getDefault());
        assertNotNull(found);
    }
}
