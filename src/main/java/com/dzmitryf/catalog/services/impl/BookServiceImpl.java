package com.dzmitryf.catalog.services.impl;

import com.dzmitryf.catalog.model.comment.Comment;
import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.repositories.BookRepository;
import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
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
    private MessageSource messageSource;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     * Create a given book. Use the returned instance for further operations as the save operation might have changed the
     * book instance completely.
     * @param book must note be {@literal null}
     * @param locale
     * @return the saved book
     * @throws Exception
     * @throws ApiServiceException if book not entity or already exists
     */
    @Transactional
    @Override
    public Book create(Book book, Locale locale) throws Exception{
        LOGGER.info(messageSource.getMessage("book.service.create.book", new Object[]{book}, locale));
        Book createdBook = new Book();
        try {
            createdBook.update(book);
            entityManager.persist(createdBook);
            entityManager.flush();
            LOGGER.info(messageSource.getMessage("book.service.created.book", new Object[]{createdBook}, locale));
            return createdBook;
        } catch (EntityExistsException e) {
            LOGGER.info(messageSource.getMessage("book.service.book.already.exist",
                    new Object[]{book.getId()}, locale));
            throw new ApiServiceException(messageSource.getMessage("book.service.book.already.exist",
                    new Object[]{book.getId()}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        } catch (IllegalArgumentException e){
            LOGGER.info(messageSource.getMessage("book.service.book.not.entity", new Object[]{book.getId()}, locale));
            throw new ApiServiceException(messageSource.getMessage("book.service.book.not.entity", new Object[]{book.getId()}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("book.service.error.create.book", null, locale), e);
            throw e;
        }
    }

    /**
     * Retrieves an book by its id.
     * @param id must not be {@literal null}.
     * @param locale
     * @return the book with the given id or {@literal null} if none found
     * @throws Exception
     * @throws ApiServiceException if book not found
     */
    @Override
    public Book getById(Long id, Locale locale) throws Exception{
        LOGGER.info(messageSource.getMessage("book.service.get.book.by.id", new Object[]{id}, locale));
        Book book = new Book();
        try {
            book = bookRepository.findOne(id);
            if (book == null){
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            LOGGER.info(messageSource.getMessage("book.service.book.id.not.found", new Object[]{id}, locale));
            throw new ApiServiceException(messageSource.getMessage("book.service.book.id.not.found", new Object[]{id}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
        LOGGER.info(messageSource.getMessage("book.service.found.book", new Object[]{book}, locale));
        return book;
    }

    /**
     * Update a given book. Use the returned instance for further operations as the save operation might have changed the
     * book instance completely.
     * @param book must note be {@literal null}
     * @param locale
     * @return the updated book
     * @throws Exception
     * @throws ApiServiceException if book not found
     */
    @Transactional
    @Override
    public Book update(Book book, Locale locale) throws Exception{
        LOGGER.info(messageSource.getMessage("book.service.update.book", new Object[]{book}, locale));
        Book updatedBook = new Book();
        try {
            if (book == null) {
                throw new IllegalArgumentException();
            }
            updatedBook = bookRepository.findOne(book.getId());
            updatedBook.update(book);
            bookRepository.flush();
            LOGGER.info(messageSource.getMessage("book.service.updated.book", new Object[]{updatedBook}, locale));
        } catch (IllegalArgumentException e) {
            LOGGER.info(messageSource.getMessage("book.service.book.id.not.found", new Object[]{0}, locale));
            throw new ApiServiceException(messageSource.getMessage("book.service.book.id.not.found", new Object[]{0}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
        return updatedBook;
    }

    /**
     * Deletes a given book.
     * @param book must note be {@literal null}
     * @param locale
     * @throws Exception
     * @throws ApiServiceException if book not found
     */
    @Override
    public void delete(Book book, Locale locale) throws Exception{
        LOGGER.info(messageSource.getMessage("book.service.delete.book", new Object[]{book}, locale));
        try {
            bookRepository.delete(book);
            LOGGER.info(messageSource.getMessage("book.service.deleted.book", new Object[]{book}, locale));
        } catch (IllegalArgumentException e) {
            LOGGER.info(messageSource.getMessage("book.service.book.id.not.found", new Object[]{0}, locale));
            throw new ApiServiceException(messageSource.getMessage("book.service.book.id.not.found", new Object[]{0}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
    }

    /**
     * Retrieve book by name
     * @param name book name
     * @param locale
     * @return the book with given name or {@literal null} if none found
     * @throws Exception
     */
    @Override
    public Book getBookByName(String name, Locale locale) throws Exception{
        LOGGER.info(messageSource.getMessage("book.service.get.book.by.name", new Object[]{name}, locale));
        Book book = bookRepository.findBookByName(name);
        if (book == null) {
            LOGGER.info(messageSource.getMessage("book.service.book.name.not.found", new Object[]{name}, locale));
            throw new ApiServiceException(messageSource.getMessage("book.service.book.name.not.found", new Object[]{name}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
        LOGGER.info(messageSource.getMessage("book.service.found.book", new Object[]{book}, locale));
        return book;
    }

    /**
     * Retrieves books with max count pages
     * @param locale
     * @return the books that sorted by descending
     * @throws Exception
     */
    @Override
    public List<Book> getBooksByCountPagesDesc(Locale locale) throws Exception{
        LOGGER.info(messageSource.getMessage("book.service.get.book.by.count.pages", null, locale));
        List<Book> books = bookRepository.findBooksByCountPagesDesc();
        LOGGER.info(messageSource.getMessage("book.service.found.books", new Object[]{books.size()}, locale));
        return books;
    }

    /**
     * Retrieves all books
     * @param locale
     * @return books list
     * @throws Exception
     */
    @Override
    public List<Book> getAllBooks(Locale locale) {
        LOGGER.info(messageSource.getMessage("book.service.get.book.all", null, locale));
        List<Book> books = bookRepository.findAll();
        LOGGER.info(messageSource.getMessage("book.service.found.books", new Object[]{books.size()}, locale));
        return books;
    }
}
