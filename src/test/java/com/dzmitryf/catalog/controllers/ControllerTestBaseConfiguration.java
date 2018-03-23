package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

import static org.mockito.Mockito.mock;

@Configuration
public class ControllerTestBaseConfiguration {

    @Bean
    public MessageSource messageSource(){
        return mock(MessageSource.class);
    }

    @Bean
    public EntityManager entityManager(){
        return mock(EntityManager.class);
    }

    @Bean
    public BookController bookController(){
        return new BookController();
    }

    @Bean
    public BookService bookService(){
        return mock(BookService.class);
    }

    @Bean
    public ObjectMapper objectMapper(){
        return mock(ObjectMapper.class);
    }
}
