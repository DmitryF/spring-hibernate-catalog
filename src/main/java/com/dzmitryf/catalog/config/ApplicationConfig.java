package com.dzmitryf.catalog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "com.dzmitryf.catalog.controllers" })
public class ApplicationConfig extends WebMvcConfigurerAdapter {

    public ApplicationConfig() {
        super();
    }
}
