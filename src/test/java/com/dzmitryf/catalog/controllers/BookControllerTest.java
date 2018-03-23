package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.common.MainWebAppInitializer;
import com.dzmitryf.catalog.config.SecurityConfig;
import com.dzmitryf.catalog.config.WebAppMvcConfig;
import com.dzmitryf.catalog.model.base.Language;
import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.model.book.Genre;
import com.dzmitryf.catalog.repositories.UserRepository;
import com.dzmitryf.catalog.services.BookService;
import com.dzmitryf.catalog.services.UserService;
import com.dzmitryf.catalog.services.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BookControllerTest.BookControllerTestContextConfiguration.class })
@WebAppConfiguration
public class BookControllerTest {

    @Configuration
    static class BookControllerTestContextConfiguration{

        @Bean
        public BookController bookController(){
            return new BookController();
        }

        @Bean
        public BookService bookService(){
            return mock(BookService.class);
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

    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookController bookController;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getAllBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(new Book("book1", "author1", 100L, Language.EN, Genre.COMEDY));
        books.add(new Book("book2", "author2", 100L, Language.EN, Genre.COMEDY));
        books.add(new Book("book3", "author3", 100L, Language.EN, Genre.COMEDY));
        books.add(new Book("book4", "author4", 100L, Language.EN, Genre.COMEDY));
        when(bookService.getAllBooks(Locale.getDefault())).thenReturn(books);

        mockMvc.perform(get("/api/book/all"))
                .andExpect(status().isOk());

        assertNotNull(bookService);
    }
}
