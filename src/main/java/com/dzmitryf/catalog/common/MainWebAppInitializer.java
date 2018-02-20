package com.dzmitryf.catalog.common;

import com.dzmitryf.catalog.config.WebAppMvcConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;

public class MainWebAppInitializer implements WebApplicationInitializer {

    private static final String DISPATCHER_SERVLET = "dispatcher";
    private static final String CONFIG_PATH = "com.dzmitryf.catalog.config";

    @Override
    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebAppMvcConfig.class);

        container.addListener(new ContextLoaderListener(context));
        container.addListener(new ServletContextListener() {
            @Override
            public void contextInitialized(ServletContextEvent servletContextEvent) {
                TestServicesRunner.run(context);
            }

            @Override
            public void contextDestroyed(ServletContextEvent servletContextEvent) {

            }
        });

        ServletRegistration.Dynamic dispatcher =
                container.addServlet(DISPATCHER_SERVLET, new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

}