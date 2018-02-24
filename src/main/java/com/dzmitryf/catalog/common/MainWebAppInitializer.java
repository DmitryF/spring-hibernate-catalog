package com.dzmitryf.catalog.common;

import com.dzmitryf.catalog.config.WebAppMvcConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;

/**
 * Application initializer
 */
public class MainWebAppInitializer implements WebApplicationInitializer {

    private static final String DISPATCHER_SERVLET = "dispatcher";
    private static final String SECURITY_FILTER = "securityFilter";
    private static final String SECURITY_FILTER_TAG_BEAN = "springSecurityFilterChain";

    @Override
    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebAppMvcConfig.class);

        container.addListener(new ContextLoaderListener(context));
        container.addListener(getServletContextListener(context));

        ServletRegistration.Dynamic dispatcher =
                container.addServlet(DISPATCHER_SERVLET, new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        container.addFilter(SECURITY_FILTER, new DelegatingFilterProxy(SECURITY_FILTER_TAG_BEAN))
                .addMappingForUrlPatterns(null, false, "/*");
    }

    /**
     * Get application startup listener
     * @param context application context
     * @return {@link ServletContextListener}
     */
    private ServletContextListener getServletContextListener(AnnotationConfigWebApplicationContext context){
        return new ServletContextListener() {
            @Override
            public void contextInitialized(ServletContextEvent servletContextEvent) {
                try {
                    TestServicesRunner.run(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void contextDestroyed(ServletContextEvent servletContextEvent) {

            }
        };
    }


}