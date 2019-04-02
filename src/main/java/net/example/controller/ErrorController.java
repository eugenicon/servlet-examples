package net.example.controller;

import net.example.view.ModelAndView;

public class ErrorController {
    public ModelAndView getErrorPage(Exception e) {
        ModelAndView view = new ModelAndView("WEB-INF/jsp/error-page.jsp");
        view.addParameter("error", e);
        return view;
    }

    public ModelAndView getViewWithSomeTestException() {
        throw new RuntimeException("Exception thrown somewhere in code");
    }
}
