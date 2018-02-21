package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public @ResponseBody User getUserByUserame(@PathVariable("username") String username){
        return userService.getUserByUsername(username);
    }

    /**
     * Get all users in database
     * @return
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public @ResponseBody List<User> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users;
    }
}
