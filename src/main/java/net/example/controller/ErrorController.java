package net.example.controller;

import net.example.servlet.GetMapping;
import net.example.servlet.PostMapping;
import net.example.view.ModelAndView;

public class ErrorController {

    @GetMapping("/error")
    @PostMapping("/error")
    public ModelAndView getErrorPage(Exception e) {
        ModelAndView view = new ModelAndView("WEB-INF/jsp/error-page.jsp");
        view.addParameter("error", e);
        return view;
    }

    @GetMapping("/problem-page")
    public ModelAndView getViewWithSomeTestException() {
        throw new RuntimeException("Exception thrown somewhere in code");
    }
}
