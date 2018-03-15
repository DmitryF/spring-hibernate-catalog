package com.dzmitryf.catalog.common;

import com.dzmitryf.catalog.model.base.Language;
import com.dzmitryf.catalog.model.base.SecurityRole;
import com.dzmitryf.catalog.model.book.Genre;
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
import java.util.Locale;
import java.util.Random;

public class TestServicesRunner {

    public static void run(AnnotationConfigWebApplicationContext context) throws Exception{
        test(context);
    }

    private static void test(AnnotationConfigWebApplicationContext context) throws Exception{

        UserRoleService userRoleService = context.getBean(UserRoleService.class);
        UserRole userRoleGuest = userRoleService.create(new UserRole(SecurityRole.ROLE_GUEST), Locale.getDefault());
        UserRole userRoleUser = userRoleService.create(new UserRole(SecurityRole.ROLE_USER), Locale.getDefault());
        UserRole userRoleAdmin = userRoleService.create(new UserRole(SecurityRole.ROLE_ADMIN), Locale.getDefault());

        BookService bookService = context.getBean(BookService.class);
        Book book1 = bookService.create(new Book("book1", "author1", 100L, Language.EN, Genre.COMEDY), Locale.getDefault());
        Book book2 = bookService.create(new Book("book2", "author2", 90L, Language.RU, Genre.DRAMA), Locale.getDefault());
        Book book3 = bookService.create(new Book("book3", "author2", 50L, Language.UNDEFINED, Genre.TRAGEDY), Locale.getDefault());
        Book book4 = bookService.create(new Book("book4", "author4", 110L, Language.EN, Genre.COMEDY), Locale.getDefault());

        Random random = new Random();
        for (int i = 5; i < 20; i++){
            Language language = Language.values()[random.nextInt(Language.values().length)];
            Genre genre = Genre.values()[random.nextInt(Genre.values().length)];
            bookService.create(new Book("book" + i, "author" + i, 100L + i * 10, language , genre), Locale.getDefault());
        }

        UserService userService = context.getBean(UserService.class);
        User user1 = userService.create(new User("username1", "1234", userRoleGuest, "Brad", "Pitt"), Locale.getDefault());
        User user2 = userService.create(new User("username2", "1234", userRoleUser, "Jeckie", "Chan"), Locale.getDefault());
        User user3 = userService.create(new User("username3", "1234", userRoleAdmin, "Petr", "Ivanov"), Locale.getDefault());
        User user4 = userService.create(new User("username4", "1234", userRoleGuest, "Ivan", "Petrov"), Locale.getDefault());

        CommentService commentService = context.getBean(CommentService.class);
        Comment comment1 = commentService.create(new Comment(user1, "commentUser1"), Locale.getDefault());
        Comment comment2 = commentService.create(new Comment(user2, "commentUser2"), Locale.getDefault());
        Comment comment3 = commentService.create(new Comment(user3, "commentUser3"), Locale.getDefault());
        Comment comment4 = commentService.create(new Comment(user4, "commentUser4"), Locale.getDefault());

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

        userService.update(user1, Locale.getDefault());
        userService.update(user2, Locale.getDefault());
        userService.update(user3, Locale.getDefault());
        userService.update(user4, Locale.getDefault());
/*
        //findUserByFirstName
        User findedUserByFirstName = userService.getUserByFirstName("Brad", Locale.getDefault());
        if (findedUserByFirstName != null){
            System.out.println(findedUserByFirstName.getFirstName());
        }

        //findUsersByCountBooksDesc
        List<User> usersByCountBooks = userService.getUsersByCountBooksDesc(Locale.getDefault());
        usersByCountBooks.forEach((User user) -> System.out.println(user.getFirstName()));

        //findBookByName
        Book findedBook = bookService.getBookByName("book1", Locale.getDefault());
        if (findedBook != null){
            System.out.println(findedBook.getName());
        }

        //findBooksByCountPagesDesc
        List<Book> booksByCountPages = bookService.getBooksByCountPagesDesc(Locale.getDefault());
        booksByCountPages.forEach((Book book)-> System.out.println(book.getCountPages()));

        //getBooksByAuthorName
        List<Book> booksByAuthorName = bookService.getBooksByAuthorName("author2", Locale.getDefault());
        booksByAuthorName.forEach((Book book)->System.out.println(book.getName()));

        //commentsByUser
        List<Comment> commentsByUser = commentService.getAllCommentsByUser(user1, Locale.getDefault());
        commentsByUser.forEach((Comment comment)->System.out.println(comment.getMessage()));*/
    }
}
