package net.example.controller;

import net.example.resolver.Component;
import net.example.resolver.GetMapping;
import net.example.resolver.PostMapping;
import net.example.view.ModelAndView;

@Component
public class ErrorController implements Controller {

    @GetMapping("/problem-page")
    public ModelAndView getViewWithSomeTestException() {
        throw new RuntimeException("Exception thrown somewhere in code");
    }

    @GetMapping("/status/error")
    @PostMapping("/status/error")
    public ModelAndView getErrorPage(Exception error) {
        ModelAndView view = new ModelAndView("status/error.jsp");
        view.addParameter("error", error);
        return view;
    }

    @GetMapping("/status/forbidden")
    public ModelAndView forbidden() {
        return new ModelAndView("status/forbidden.jsp");
    }

    @GetMapping("/status/not-found")
    public ModelAndView notFound() {
        return new ModelAndView("status/not-found.jsp");
    }
}
