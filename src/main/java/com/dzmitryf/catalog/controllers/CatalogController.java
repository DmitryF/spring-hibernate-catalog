package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.services.UserService;
import com.dzmitryf.catalog.services.impl.ApiServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("api")
public class CatalogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    /**
     * Login form
     * @param username
     * @return the user with given username
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody User login(@RequestParam(value = "username", required = false) String username, Locale locale) throws ApiServiceException {
        try {
            LOGGER.info(messageSource.getMessage("catalog.controller.login.complete", new Object[]{username}, locale));
            User user = userService.getUserByUsername(username, locale);
            LOGGER.info(messageSource.getMessage("catalog.controller.login.response.user", new Object[]{user}, locale));
            return user;
        }
        catch (ApiServiceException e) {
            throw e;
        } catch (Exception e){
            LOGGER.error(messageSource.getMessage("catalog.controller.error.login", new Object[]{username}, locale), e);
            throw new ApiServiceException(messageSource.getMessage("application.error.internal", null, locale));
        }
    }

    /**
     * Access denied redirect
     * @param locale current user {@link Locale}
     * @return {@link ErrorResponse} with status 403
     */
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ErrorResponse> accessDenied(Locale locale) throws ApiServiceException{
        try {
            LOGGER.info(messageSource.getMessage("security.access_denied", null, locale));
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                    messageSource.getMessage("security.access_denied", null, locale));
            return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.FORBIDDEN);
        } catch (Exception e){
            LOGGER.error(messageSource.getMessage("catalog.controller.error.access.denied", null, locale));
            throw new ApiServiceException(messageSource.getMessage("application.error.internal", null, locale));
        }
    }

    /**
     * Catalog controller exception handler
     * @param apiException
     * @return {@link ErrorResponse} with given status from {@link ApiServiceException}
     */
    @ExceptionHandler(ApiServiceException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ApiServiceException apiException){
        ErrorResponse errorResponse = new ErrorResponse(apiException.getStatusCode().value(), apiException.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, apiException.getStatusCode());
    }
}
