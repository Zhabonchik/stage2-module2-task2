package com.example.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.time.LocalDateTime;

public class ContextListener implements ServletContextListener{
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        ServletContext context = sce.getServletContext();
        context.setAttribute("servletTimeInit", LocalDateTime.now());
    }
}
