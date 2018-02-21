package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.services.UserRoleService;
import com.dzmitryf.catalog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("")
public class CatalogController {

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
        User user = userService.getUserByUsername(username);
        return user;
    }

    /**
     * Get all users in database
     * @return
     */
    @RequestMapping(value = "/admin/all", method = RequestMethod.GET)
    public @ResponseBody List<User> adminAll() {
        List<User> users = userService.getAllUsers();
        return users;
    }

    /**
     * Access denied redirect
     * @param locale current user {@link Locale}
     * @return locale message
     */
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public @ResponseBody String accessDenied(Locale locale) {
        return messageSource.getMessage("security.access_denied", null, locale);
    }
}
