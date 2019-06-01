package net.example.controller;


import net.example.resolver.Component;
import net.example.resolver.PostMapping;
import net.example.servlet.SessionListener;
import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

import javax.servlet.http.HttpSession;

@Component
public class LocalizationController implements Controller {

    @PostMapping("/set-language")
    public View setLanguage(String language, String refererUrl, HttpSession session) {
        session.setAttribute(SessionListener.LANGUAGE_ATTRIBUTE, language);
        return new RedirectView(new ModelAndView(refererUrl));
    }
}
