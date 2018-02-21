package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * Get all books
     * @return
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public @ResponseBody
    List<Book> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return books;
    }

    /**
     * Get book by id
     * @return the book with given id
     */
    @RequestMapping(value = "{bookId}", method = RequestMethod.GET)
    public @ResponseBody Book getCmmentById(@PathVariable("bookId") Long bookId) {
        Book book = bookService.getById(bookId);
        return book;
    }
}
