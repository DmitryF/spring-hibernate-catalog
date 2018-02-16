package com.dzmitryf.catalog.common;

import com.dzmitryf.catalog.model.User;
import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.model.comment.Comment;
import com.dzmitryf.catalog.services.BookService;
import com.dzmitryf.catalog.services.CommentService;
import com.dzmitryf.catalog.services.UserService;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.List;

public class TestServicesRunner {

    public static void run(AnnotationConfigWebApplicationContext context){
        test(context);
    }

    private static void test(AnnotationConfigWebApplicationContext context){

        BookService bookService = context.getBean(BookService.class);
        Book book1 = bookService.create(new Book("book1", "author1", 100L));
        Book book2 = bookService.create(new Book("book2", "author2", 90L));
        Book book3 = bookService.create(new Book("book3", "author2", 50L));
        Book book4 = bookService.create(new Book("book4", "author4", 110L));

        UserService userService = context.getBean(UserService.class);
        User user1 = userService.create(new User("Brad", "Pitt"));
        User user2 = userService.create(new User("Jeckie", "Chan"));
        User user3 = userService.create(new User("Petr", "Ivanov"));
        User user4 = userService.create(new User("Ivan", "Petrov"));

        CommentService commentService = context.getBean(CommentService.class);
        Comment comment1 = commentService.create(new Comment(user1, "commentUser1"));
        Comment comment2 = commentService.create(new Comment(user2, "commentUser2"));
        Comment comment3 = commentService.create(new Comment(user3, "commentUser3"));
        Comment comment4 = commentService.create(new Comment(user4, "commentUser4"));

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

        userService.update(user1);
        userService.update(user2);
        userService.update(user3);
        userService.update(user4);

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
