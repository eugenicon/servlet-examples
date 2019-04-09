package net.example.controller;

import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

public class WelcomeController {

    public View doWelcome() {
        return new ModelAndView("WEB-INF/html/welcome.html");
    }

    public View doWelcomeRedirect() {
        return new RedirectView(new ModelAndView("welcome"));
    }

    public View showTemplatePage() {
        return new ModelAndView("WEB-INF/jsp/example.jsp");
    }
}
