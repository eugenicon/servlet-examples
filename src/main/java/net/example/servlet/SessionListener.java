package net.example.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final Logger LOGGER = LogManager.getLogger(SessionListener.class);
    public static final String LANGUAGE_ATTRIBUTE = "lang";
    private static final String DEFAULT_LANGUAGE = "en";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute(LANGUAGE_ATTRIBUTE, DEFAULT_LANGUAGE);
        LOGGER.info("Set default language to {}", DEFAULT_LANGUAGE);
    }
}
