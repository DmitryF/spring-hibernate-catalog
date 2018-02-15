package com.dzmitryf.catalog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class CatalogController {


    @RequestMapping("/test")
    public String test(){
        return "test";
    }
}
