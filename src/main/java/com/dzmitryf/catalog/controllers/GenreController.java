package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.book.Genre;
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
@RequestMapping("api/genre")
public class GenreController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private GenreService genreService;

    /**
     * Get all genres
     * @param locale
     * @return the all genres or {@literal ApiServiceException} if none found
     * @throws ApiServiceException if genres not found
     */
    @CrossOrigin
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody List<Genre> getAllGenres(Locale locale) throws Exception{
        try {
            LOGGER.info(messageSource.getMessage("genre.controller.get.genre.all", null, locale));
            List<Genre> genres = genreService.getAllGenres(locale);
            LOGGER.info(messageSource.getMessage("genre.controller.response.genres", new Object[]{genres.size()}, locale));
            return genres;
        } catch (ApiServiceException e){
            throw e;
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("genre.controller.error.get.genre.all", null, locale), e);
            throw new ApiServiceException(messageSource.getMessage("application.error.internal", null, locale));
        }
    }

    /**
     * Genre controller exception handler
     * @param apiException
     * @return {@link ErrorResponse} with given status from {@link ApiServiceException}
     */
    @ExceptionHandler(ApiServiceException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ApiServiceException apiException){
        ErrorResponse errorResponse = new ErrorResponse(apiException.getStatusCode().value(), apiException.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, apiException.getStatusCode());
    }
}
