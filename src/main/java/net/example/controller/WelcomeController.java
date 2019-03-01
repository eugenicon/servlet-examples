package net.example.controller;

import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

public class WelcomeController {

    public View doWelcome() {
        return new ModelAndView("index.html");
    }

    public View doWelcomeRedirect() {
        return new RedirectView(new ModelAndView("welcome"));
    }
}
