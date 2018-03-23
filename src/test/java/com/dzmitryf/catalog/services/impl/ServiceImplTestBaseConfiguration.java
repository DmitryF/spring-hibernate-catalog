package com.dzmitryf.catalog.services.impl;

import com.dzmitryf.catalog.repositories.UserRepository;
import com.dzmitryf.catalog.services.UserService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

import static org.mockito.Mockito.mock;

@Configuration
public class ServiceImplTestBaseConfiguration {

    @Bean
    public MessageSource messageSource(){
        return mock(MessageSource.class);
    }

    @Bean
    public EntityManager entityManager(){
        return mock(EntityManager.class);
    }

    @Bean
    public UserService userService(){
        return (UserService) new UserServiceImpl();
    }

    @Bean
    public UserRepository userRepository(){
        return mock(UserRepository.class);
    }
}
