package com.dzmitryf.catalog.services.impl;

import com.dzmitryf.catalog.repositories.BookRepository;
import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public Book create(Book entity, Locale locale) throws Exception{
        LOGGER.info("Creating a new book: {}", entity);
        Book book = new Book();
        try {
            book.update(entity);
            entityManager.persist(book);
            entityManager.flush();
            LOGGER.info("Created a new book: {}", book);
        } catch (Exception e){
            LOGGER.error("Error while creating a new book: ", e);
        }
        return book;
    }

    @Override
    public Book getById(Long id, Locale locale) throws Exception{
        LOGGER.info("Finding a book by id: {}", id);
        Book book = new Book();
        try {
            book = bookRepository.findOne(id);
            LOGGER.info("Found a book : {}", book);
        } catch (Exception e) {
            LOGGER.error("Error while finding a book by id: ", e);
        }
        return book;
    }

    @Transactional
    @Override
    public Book update(Book entity, Locale locale) throws Exception{
        LOGGER.info("Updating a book: {}", entity);
        Book book = new Book();
        try {
            book = bookRepository.findOne(entity.getId());
            book.update(entity);
            bookRepository.flush();
            LOGGER.info("Updated a book: {}", book);
        } catch (Exception e){
            LOGGER.error("Error while updating a book: ", e);
        }
        return book;
    }

    @Override
    public void delete(Book entity, Locale locale) throws Exception{
        LOGGER.info("Deleting a book: {}", entity);
        try {
            bookRepository.delete(entity);
            LOGGER.info("Deleted a book: {}", entity);
        } catch (Exception e){
            LOGGER.error("Error while deleting a book: ", e);
        }
    }

    @Override
    public Book getBookByName(String name, Locale locale) throws Exception{
        LOGGER.info("Finding a book by name: {}", name);
        Book book = bookRepository.findBookByName(name);
        LOGGER.info("Found a book: {}", book);
        return book;
    }

    @Override
    public List<Book> getBooksByCountPagesDesc(Locale locale) throws Exception{
        LOGGER.info("Finding books by count pages");
        List<Book> books = bookRepository.findBooksByCountPagesDesc();
        LOGGER.info("Found {} books", books.size());
        return books;
    }

    @Override
    public List<Book> getAllBooks(Locale locale) {
        LOGGER.info("Finding all books");
        List<Book> books = bookRepository.findAll();
        LOGGER.info("Found {} books", books.size());
        return books;
    }

    @Transactional
    @Override
    public List<Book> getBooksByAuthorName(String authorName, Locale locale) throws Exception{
        LOGGER.info("Finding books by author name: {}", authorName);
        List<Book> books = new ArrayList<>();
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM hbschema.books WHERE author_name = ?1", Book.class);
            query.setParameter(1, authorName);
            books = query.getResultList();
            LOGGER.info("Found {} books", books.size());
        } catch (Exception e){
            LOGGER.error("Error while finding a book by author name: ", e);
        }
        return books;
    }
}
