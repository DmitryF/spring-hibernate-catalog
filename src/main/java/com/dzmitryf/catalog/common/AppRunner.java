package com.dzmitryf.catalog.common;

import com.dzmitryf.catalog.config.PersistenceConfig;
import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.services.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class AppRunner {

    public static void main(String[] args){
        ApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);

        BookService bookService = context.getBean(BookService.class);
        bookService.save(new Book("book1", "author1", 100L));
        bookService.save(new Book("book2", "author2", 90L));
        bookService.save(new Book("book3", "author2", 50L));
        bookService.save(new Book("book4", "author4", 110L));

        Book findedBook = bookService.findBookByName("book1");
        if (findedBook != null){
            System.out.println(findedBook.getName());
        }

        List<Book> booksByCountPages = bookService.getBooksByCountPagesDesc();
        for (Book book : booksByCountPages) {
            System.out.println(book.getCountPages());
        }

        List<Book> booksByAuthorName = bookService.getBooksByAuthorName("author2");
        for (Book book : booksByAuthorName) {
            System.out.println(book.getName());
        }
    }
}
