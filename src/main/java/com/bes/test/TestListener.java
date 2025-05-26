package com.bes.test;

import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class TestListener implements ServletContextListener {
    private ConfigurableApplicationContext appContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        appContext = new SpringApplicationBuilder()
                .sources(TestServerApplication.class)
                .web(WebApplicationType.REACTIVE)
                .bannerMode(Banner.Mode.OFF)
                .logStartupInfo(false)
                .run();

        sce.getServletContext().setAttribute("springContext", appContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (appContext != null) {
            appContext.close();
        }
    }
}