package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.User;
import com.dzmitryf.catalog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "{firstName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getUserByFirstName(@PathVariable("firstName") String firstName){
        return userService.getUserByFirstName(firstName).toString();
    }
}
