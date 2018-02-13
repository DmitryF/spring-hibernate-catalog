package com.dzmitryf.catalog.common;

import com.dzmitryf.catalog.config.PersistenceConfig;
import com.dzmitryf.catalog.model.User;
import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.services.BookService;
import com.dzmitryf.catalog.services.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class AppRunner {

    public static void main(String[] args){
        ApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);

        UserService userService = context.getBean(UserService.class);
        User user1 = new User("Brad", "Pitt");
        User user2 = new User("Jeckie", "Chan");
        User user3 = new User("Petr", "Ivanov");
        User user4 = new User("Ivan", "Petrov");

        BookService bookService = context.getBean(BookService.class);
        Book book1 = new Book("book1", "author1", 100L);
        Book book2 = new Book("book2", "author2", 90L);
        Book book3 = new Book("book3", "author2", 50L);
        Book book4 = new Book("book4", "author4", 110L);

        book1.getUsers().add(user1);

        bookService.save(book1);
        bookService.save(book2);
        bookService.save(book3);
        bookService.save(book4);

        user1.getBooks().add(book1);
        user1.getBooks().add(book2);

        user2.getBooks().add(book3);

        user3.getBooks().add(book1);
        user3.getBooks().add(book2);
        user3.getBooks().add(book3);
        user3.getBooks().add(book4);

        user4.getBooks().add(book2);
        user4.getBooks().add(book3);
        user4.getBooks().add(book4);

        userService.save(user1);
        userService.save(user2);
        userService.save(user3);
        userService.save(user4);

        //findUserByFirstName
        User findedUserByFirstName = userService.findUserByFirstName("Brad");
        if (findedUserByFirstName != null){
            System.out.println(findedUserByFirstName.getFirstName());
        }

        //getUsersByCountBooksDesc
        List<User> usersByCountBooks = userService.getUsersByCountBooksDesc();
        for (User user: usersByCountBooks) {
            System.out.println(user.getFirstName());
        }

        //findBookByName
        Book findedBook = bookService.findBookByName("book1");
        if (findedBook != null){
            System.out.println(findedBook.getName());
        }

        //getBooksByCountPagesDesc
        List<Book> booksByCountPages = bookService.getBooksByCountPagesDesc();
        for (Book book : booksByCountPages) {
            System.out.println(book.getCountPages());
        }

        //getBooksByAuthorName
        List<Book> booksByAuthorName = bookService.getBooksByAuthorName("author2");
        for (Book book : booksByAuthorName) {
            System.out.println(book.getName());
        }
    }
}
