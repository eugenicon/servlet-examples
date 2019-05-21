package net.example.controller;

import net.example.servlet.GetMapping;
import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

public class WelcomeController {

    @GetMapping("/welcome")
    public View doWelcome() {
        return new ModelAndView("welcome.jsp");
    }

    @GetMapping("/")
    public View doWelcomeRedirect() {
        return new RedirectView(new ModelAndView("welcome"));
    }

    @GetMapping("/template-example")
    public View showTemplatePage() {
        return new ModelAndView("example.jsp");
    }
}
