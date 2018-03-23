package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.base.Language;
import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.model.book.Genre;
import com.dzmitryf.catalog.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ControllerTestBaseConfiguration.class })
@WebAppConfiguration
public class BookControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookController bookController;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getAllBooks() throws Exception {

        assertNotNull(bookService);
        assertNotNull(mockMvc);
        assertNotNull(objectMapper);

        List<Book> books = new ArrayList<>();
        books.add(new Book("book1", "author1", 100L, Language.EN, Genre.COMEDY));
        books.add(new Book("book2", "author2", 100L, Language.EN, Genre.COMEDY));
        books.add(new Book("book3", "author3", 100L, Language.EN, Genre.COMEDY));
        books.add(new Book("book4", "author4", 100L, Language.EN, Genre.COMEDY));
        when(bookService.getAllBooks(any(Locale.class))).thenReturn(books);

        mockMvc.perform(get("/api/book/all")
                .content(objectMapper.writeValueAsBytes(Locale.getDefault())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(books.size()))
                .andExpect(jsonPath("$[0].name").value(books.get(0).getName()));

        verify(bookService, times(1)).getAllBooks(any(Locale.class));
        verifyNoMoreInteractions(bookService);
    }
}
