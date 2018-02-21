package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("")
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
    public @ResponseBody User login(@RequestParam(value = "username", required = false) String username) {
        LOGGER.info("Request user login complete: {}", username);
        User user = userService.getUserByUsername(username);
        LOGGER.info("Response has user: {}", user);
        return user;
    }

    /**
     * Access denied redirect
     * @param locale current user {@link Locale}
     * @return locale message
     */
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public @ResponseBody String accessDenied(Locale locale) {
        LOGGER.info("Request error 403. Access denied");
        return messageSource.getMessage("security.access_denied", null, locale);
    }
}
