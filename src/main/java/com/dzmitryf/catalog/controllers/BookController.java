package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/book")
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    /**
     * Get all books
     * @return
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public @ResponseBody
    List<Book> getAllBooks(Locale locale) throws Exception{
        LOGGER.info("Request get all books");
        List<Book> books = bookService.getAllBooks(locale);
        LOGGER.info("Response has {} books", books.size());
        return books;
    }

    /**
     * Get book by id
     * @return the book with given id
     */
    @RequestMapping(value = "{bookId}", method = RequestMethod.GET)
    public @ResponseBody Book getCmmentById(@PathVariable("bookId") Long bookId, Locale locale) throws Exception{
        LOGGER.info("Request get book by id: {}", bookId);
        Book book = bookService.getById(bookId, locale);
        LOGGER.info("Response has book: {}", book);
        return book;
    }
}
