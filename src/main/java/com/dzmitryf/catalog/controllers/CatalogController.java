package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.base.SecurityRole;
import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.model.user.UserRole;
import com.dzmitryf.catalog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class CatalogController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody User login(@RequestParam(value = "error", required = false) String error,
                      @RequestParam(value = "logout", required = false) String logout) {

        User user = new User("username1", "1234", new UserRole(SecurityRole.ROLE_GUEST), "firstName", "lastName");
        return user;

    }
}
