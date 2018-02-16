package com.dzmitryf.catalog.common;

import com.dzmitryf.catalog.config.PersistenceConfig;
import com.dzmitryf.catalog.model.User;
import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.model.comment.Comment;
import com.dzmitryf.catalog.services.BookService;
import com.dzmitryf.catalog.services.CommentService;
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

        CommentService commentService = context.getBean(CommentService.class);
        Comment comment1 = new Comment(user1, "commentUser1");
        Comment comment2 = new Comment(user2, "commentUser2");
        Comment comment3 = new Comment(user3, "commentUser3");
        Comment comment4 = new Comment(user4, "commentUser4");

        BookService bookService = context.getBean(BookService.class);
        Book book1 = new Book("book1", "author1", 100L);
        Book book2 = new Book("book2", "author2", 90L);
        Book book3 = new Book("book3", "author2", 50L);
        Book book4 = new Book("book4", "author4", 110L);

        bookService.create(book1);
        bookService.create(book2);
        bookService.create(book3);
        bookService.create(book4);

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

        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        userService.create(user4);

        commentService.create(comment1);
        commentService.create(comment2);
        commentService.create(comment3);
        commentService.create(comment4);

        //findUserByFirstName
        User findedUserByFirstName = userService.getUserByFirstName("Brad");
        if (findedUserByFirstName != null){
            System.out.println(findedUserByFirstName.getFirstName());
        }

        //findUsersByCountBooksDesc
        List<User> usersByCountBooks = userService.getUsersByCountBooksDesc();
        usersByCountBooks.forEach((User user) -> System.out.println(user.getFirstName()));

        //findBookByName
        Book findedBook = bookService.getBookByName("book1");
        if (findedBook != null){
            System.out.println(findedBook.getName());
        }

        //findBooksByCountPagesDesc
        List<Book> booksByCountPages = bookService.getBooksByCountPagesDesc();
        booksByCountPages.forEach((Book book)-> System.out.println(book.getCountPages()));

        //getBooksByAuthorName
        List<Book> booksByAuthorName = bookService.getBooksByAuthorName("author2");
        booksByAuthorName.forEach((Book book)->System.out.println(book.getName()));

        //commentsByUser
        List<Comment> commentsByUser = commentService.getAllCommentsOfUser(user1);
        commentsByUser.forEach((Comment comment)->System.out.println(comment.getMessage()));
    }
}
