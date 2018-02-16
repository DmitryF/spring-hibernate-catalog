package com.dzmitryf.catalog.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class CatalogController {

    @RequestMapping(method = RequestMethod.GET)
    public String test(){
        return "complete";
    }
}