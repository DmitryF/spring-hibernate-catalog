package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.services.UserService;
import com.dzmitryf.catalog.services.impl.ApiServiceException;
import com.dzmitryf.catalog.services.impl.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    /**
     * Get user bu username
     * @param username
     * @param locale
     * @return the user with given username or {@literal ApiServiceException} if none found
     * @throws Exception
     * @throws ApiServiceException if user not found
     */
    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public @ResponseBody User getUserByUserame(@PathVariable("username") String username, Locale locale) throws ApiServiceException {
        try {
            LOGGER.info(messageSource.getMessage("user.controller.get.user.by.username", new Object[]{username}, locale));
            User user = userService.getUserByUsername(username, locale);
            LOGGER.info(messageSource.getMessage("user.controller.response.user", new Object[]{user}, locale));
            return user;
        } catch (ApiServiceException e) {
            throw e;
        } catch (Exception e){
            LOGGER.error(messageSource.getMessage("user.controller.error.get.user.by.username", new Object[]{username}, locale), e);
            throw new ApiServiceException(messageSource.getMessage("application.error.internal", null, locale));
        }
    }

    /**
     * Get all users in database
     * @param locale
     * @return users list or {@literal ApiServiceException} if none found
     * @throws ApiServiceException if users not found
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public @ResponseBody List<User> getAllUsers(Locale locale) throws Exception {
        try {
            LOGGER.info(messageSource.getMessage("user.controller.get.user.all", null, locale));
            List<User> users = userService.getAllUsers(locale);
            LOGGER.info(messageSource.getMessage("user.controller.response.users", new Object[]{users.size()}, locale));
            return users;
        } catch (ApiServiceException e){
            throw e;
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("user.controller.error.get.user.all", null, locale), e);
            throw new ApiServiceException(messageSource.getMessage("application.error.internal", null, locale));
        }
    }

    /**
     * User controller exception handler
     * @param apiException
     * @return {@link ErrorResponse} with given status from {@link ApiServiceException}
     */
    @ExceptionHandler(ApiServiceException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ApiServiceException apiException){
        ErrorResponse errorResponse = new ErrorResponse(apiException.getStatusCode().value(), apiException.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, apiException.getStatusCode());
    }
}
