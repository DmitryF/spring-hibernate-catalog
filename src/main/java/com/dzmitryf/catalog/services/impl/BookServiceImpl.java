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
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public Book create(Book entity) {
        LOGGER.info("Creating a new book: {}", entity);
        try {
            entityManager.persist(entity);
            entityManager.flush();
            LOGGER.info("Created a new book: {}", entity);
        } catch (Exception e){
            LOGGER.error("Error while creating a new book: ", e);
            return null;
        }
        return entity;
    }

    @Override
    public Book getById(Long id) {
        LOGGER.info("Finding a book by id: {}", id);
        Book book = null;
        try {
            book = bookRepository.findOne(id);
        } catch (Exception e) {
            LOGGER.error("Error while finding a book by id: ", e);
            return null;
        }
        LOGGER.info("Found a book : {}", book);
        return book;
    }

    @Transactional
    @Override
    public Book update(Book entity) {
        LOGGER.info("Updating a book: {}", entity);
        try {
            Book currentBook = bookRepository.findOne(entity.getId());
            currentBook.update(entity);
            bookRepository.flush();
            LOGGER.info("Updated a book: {}", currentBook);
        } catch (Exception e){
            LOGGER.error("Error while updating a book: ", e);
            return null;
        }
        return entity;
    }

    @Override
    public void delete(Book entity) {
        LOGGER.info("Deleting a book: {}", entity);
        try {
            bookRepository.delete(entity);
            LOGGER.info("Deleted a book: {}", entity);
        } catch (Exception e){
            LOGGER.error("Error while deleting a book: ", e);
        }
    }

    public Book getBookByName(String name) {
        LOGGER.info("Finding a book by name: {}", name);
        Book book = bookRepository.findBookByName(name);
        LOGGER.info("Found a book: {}", book);
        return book;
    }

    public List<Book> getBooksByCountPagesDesc() {
        LOGGER.info("Finding books by count pages");
        List<Book> books = bookRepository.findBooksByCountPagesDesc();
        LOGGER.info("Found {} books", books.size());
        return books;
    }

    @Transactional
    public List<Book> getBooksByAuthorName(String authorName) {
        LOGGER.info("Finding books by author name: {}", authorName);
        List<Book> books = null;
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM hbschema.books WHERE author_name = ?1", Book.class);
            query.setParameter(1, authorName);
            books = query.getResultList();
            entityManager.flush();
            LOGGER.info("Found {} books", books.size());
        } catch (Exception e){
            LOGGER.error("Error while finding a book by author name: ", e);
        }
        return books;
    }
}
