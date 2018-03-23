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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceImplTestBaseConfiguration.class })
public class UserServiceImplTest {

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
