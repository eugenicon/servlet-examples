package net.example.controller;

import net.example.view.ModelAndView;
import net.example.view.View;

public class WelcomeController {

    public View doWelcome() {
        return new ModelAndView("/index.html");
    }
}
