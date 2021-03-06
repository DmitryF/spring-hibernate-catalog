package com.dzmitryf.catalog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@EnableWebMvc
@Configuration
@Import({DatabaseConfig.class,
        SecurityConfig.class,
        LocaleConfig.class})
@ComponentScan(basePackages = { "com.dzmitryf.catalog.controllers" })
public class WebAppMvcConfig extends WebMvcConfigurerAdapter {

    public WebAppMvcConfig() {
        super();
    }

    /**
     * Configure Json converters
     * @param converters list
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder();
        jackson2ObjectMapperBuilder.failOnEmptyBeans(false);
        converters.add(new MappingJackson2HttpMessageConverter(jackson2ObjectMapperBuilder.build()));
        Jaxb2RootElementHttpMessageConverter jaxb2RootElementHttpMessageConverter = new Jaxb2RootElementHttpMessageConverter();
        converters.add(jaxb2RootElementHttpMessageConverter);
    }

    /**
     * Configure views
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/app-books/*").setViewName("forward:/index.html");
    }

    /**
     * Configure resources
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("*").addResourceLocations("");
    }
}
