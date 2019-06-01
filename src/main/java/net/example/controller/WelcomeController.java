package net.example.controller;

import net.example.resolver.Component;
import net.example.resolver.GetMapping;
import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

@Component
public class WelcomeController implements Controller {

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
