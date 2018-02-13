package com.dzmitryf.catalog.services;

import com.dzmitryf.catalog.model.book.Book;

import java.util.List;

public interface BookService {

    Book save(Book book);

    Book findBookByName(String name);

    List<Book> getBooksByCountPagesDesc();

    List<Book> getBooksByAuthorName(String authorName);
}
