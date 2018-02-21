package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public @ResponseBody User getUserByFirstName(@PathVariable("username") String username){
        return userService.getUserByUsername(username);
    }
}
