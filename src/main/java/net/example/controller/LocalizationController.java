package net.example.controller;


import net.example.servlet.PostMapping;
import net.example.servlet.SessionListener;
import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

import javax.servlet.http.HttpSession;

public class LocalizationController {

    @PostMapping("/set-language")
    public View setLanguage(String language, String refererUrl, HttpSession session) {
        session.setAttribute(SessionListener.LANGUAGE_ATTRIBUTE, language);
        return new RedirectView(new ModelAndView(refererUrl));
    }
}
