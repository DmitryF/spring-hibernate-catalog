package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.services.UserService;
import com.dzmitryf.catalog.services.impl.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public @ResponseBody User getUserByUserame(@PathVariable("username") String username){
        LOGGER.info("Request get user by username: {}", username);
        User user = userService.getUserByUsername(username);
        LOGGER.info("Response has user: {}", user);
        return user;
    }

    /**
     * Get all users in database
     * @return
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public @ResponseBody List<User> getAllUsers() {
        LOGGER.info("Request get all users");
        List<User> users = userService.getAllUsers();
        LOGGER.info("Response has {} users", users.size());
        return users;
    }
}
