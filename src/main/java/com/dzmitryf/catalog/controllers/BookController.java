package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.model.book.Genre;
import com.dzmitryf.catalog.services.BookService;
import com.dzmitryf.catalog.services.GenreService;
import com.dzmitryf.catalog.services.impl.ApiServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api/book")
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private BookService bookService;

    @Autowired
    private GenreService genreService;

    /**
     * Get all books
     * @return books list or {@literal ApiServiceException} if none found
     * @throws ApiServiceException if books not found
     */
    @CrossOrigin
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public @ResponseBody List<Book> getAllBooks(Locale locale) throws Exception{
        try {
            LOGGER.info(messageSource.getMessage("book.controller.get.book.all", null, locale));
            List<Book> books = bookService.getAllBooks(locale);
            LOGGER.info(messageSource.getMessage("book.controller.response.books", new Object[]{books.size()}, locale));
            return books;
        } catch (ApiServiceException e){
            throw e;
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("book.controller.error.get.book.all", null, locale), e);
            throw new ApiServiceException(messageSource.getMessage("application.error.internal", null, locale));
        }
    }

    /**
     * Get book by id
     * @param bookId
     * @param locale
     * @return the book with given id or {@literal ApiServiceException} if none found
     * @throws ApiServiceException if book not found
     */
    @RequestMapping(value = "{bookId}", method = RequestMethod.GET)
    public @ResponseBody Book getBookById(@PathVariable("bookId") Long bookId, Locale locale) throws Exception{
        try {
            LOGGER.info(messageSource.getMessage("book.controller.get.book.by.id", new Object[]{bookId}, locale));
            Book book = bookService.getById(bookId, locale);
            LOGGER.info(messageSource.getMessage("book.controller.response.book", new Object[]{book}, locale));
            return book;
        } catch (ApiServiceException e){
            throw e;
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("book.controller.error.get.book.by.id", new Object[]{bookId}, locale), e);
            throw new ApiServiceException(messageSource.getMessage("application.error.internal", null, locale));
        }
    }

    /**
     * Get books by genre
     * @param genreName
     * @param locale
     * @return the books with given genre or {@literal ApiServiceException} if none found
     * @throws ApiServiceException if books not found
     */
    @CrossOrigin
    @RequestMapping(value = "/genre/{genre}", method = RequestMethod.GET)
    public @ResponseBody List<Book> getBooksByGenres(@PathVariable("genre") String genreName, Locale locale) throws Exception{
        try {
            Genre genre = genreService.getGenreByName(genreName, locale);
            LOGGER.info(messageSource.getMessage("book.controller.get.book.by.genre", new Object[]{genre}, locale));
            List<Book> books = bookService.getBookByGenre(genre, locale);
            LOGGER.info(messageSource.getMessage("book.controller.response.books", new Object[]{books.size()}, locale));
            return books;
        } catch (ApiServiceException e){
            throw e;
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("book.controller.error.get.book.by.genre", new Object[]{genreName}, locale), e);
            throw new ApiServiceException(messageSource.getMessage("application.error.internal", null, locale));
        }
    }

    /**
     * Book controller exception handler
     * @param apiException
     * @return {@link ErrorResponse} with given status from {@link ApiServiceException}
     */
    @ExceptionHandler(ApiServiceException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ApiServiceException apiException){
        ErrorResponse errorResponse = new ErrorResponse(apiException.getStatusCode().value(), apiException.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, apiException.getStatusCode());
    }
}
