package com.dzmitryf.catalog.common;

import com.dzmitryf.catalog.model.base.SecurityRole;
import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.model.comment.Comment;
import com.dzmitryf.catalog.model.user.UserRole;
import com.dzmitryf.catalog.services.BookService;
import com.dzmitryf.catalog.services.CommentService;
import com.dzmitryf.catalog.services.UserRoleService;
import com.dzmitryf.catalog.services.UserService;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.List;

public class TestServicesRunner {

    public static void run(AnnotationConfigWebApplicationContext context){
        test(context);
    }

    private static void test(AnnotationConfigWebApplicationContext context){

        UserRoleService userRoleService = context.getBean(UserRoleService.class);
        UserRole userRoleGuest = userRoleService.create(new UserRole(SecurityRole.ROLE_GUEST));
        UserRole userRoleUser = userRoleService.create(new UserRole(SecurityRole.ROLE_USER));
        UserRole userRoleAdmin = userRoleService.create(new UserRole(SecurityRole.ROLE_ADMIN));

        BookService bookService = context.getBean(BookService.class);
        Book book1 = bookService.create(new Book("book1", "author1", 100L));
        Book book2 = bookService.create(new Book("book2", "author2", 90L));
        Book book3 = bookService.create(new Book("book3", "author2", 50L));
        Book book4 = bookService.create(new Book("book4", "author4", 110L));

        UserService userService = context.getBean(UserService.class);
        User user1 = userService.create(new User("username1", "1234", userRoleGuest, "Brad", "Pitt"));
        User user2 = userService.create(new User("username2", "1234", userRoleUser, "Jeckie", "Chan"));
        User user3 = userService.create(new User("username3", "1234", userRoleAdmin, "Petr", "Ivanov"));
        User user4 = userService.create(new User("username4", "1234", userRoleGuest, "Ivan", "Petrov"));

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
        List<Comment> commentsByUser = commentService.getAllCommentsByUser(user1);
        commentsByUser.forEach((Comment comment)->System.out.println(comment.getMessage()));
    }
}
