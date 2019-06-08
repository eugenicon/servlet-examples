package net.example.controller;

import net.example.resolver.Component;
import net.example.resolver.GetMapping;
import net.example.resolver.PostMapping;
import net.example.view.ModelAndView;

@Component
public class ErrorController implements Controller {

    @GetMapping("/error")
    @PostMapping("/error")
    public ModelAndView getErrorPage(Exception error) {
        ModelAndView view = new ModelAndView("error-page.jsp");
        view.addParameter("error", error);
        return view;
    }

    @GetMapping("/problem-page")
    public ModelAndView getViewWithSomeTestException() {
        throw new RuntimeException("Exception thrown somewhere in code");
    }

    @GetMapping("/status/forbidden")
    public ModelAndView forbidden() {
        return new ModelAndView("status/forbidden.jsp");
    }
}
