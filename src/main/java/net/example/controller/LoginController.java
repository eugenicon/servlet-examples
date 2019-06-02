package net.example.controller;

import net.example.data.dto.UserLoginDto;
import net.example.data.validation.Valid;
import net.example.resolver.Component;
import net.example.resolver.ExceptionMapping;
import net.example.resolver.GetMapping;
import net.example.resolver.PostMapping;
import net.example.service.AuthenticationService;
import net.example.service.ServiceException;
import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

import javax.servlet.http.HttpSession;

@Component
public class LoginController implements Controller {
    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    public View login() {
        return new ModelAndView("login.jsp");
    }

    @PostMapping("/login")
    public View login(@Valid UserLoginDto userLogin, HttpSession session) throws ServiceException {
        authenticationService.login(session, userLogin.getUserName(), userLogin.getPassword());
        return new RedirectView(new ModelAndView("user/list"));
    }

    @GetMapping("/logout")
    public View logout(HttpSession session) {
        authenticationService.logout(session);
        return new RedirectView(new ModelAndView("welcome"));
    }

    @ExceptionMapping(ServiceException.class)
    public View showUserAddErrors(ServiceException error, UserLoginDto userLogin) {
        ModelAndView modelAndView = new ModelAndView("login.jsp");
        modelAndView.addParameter("login", userLogin);
        return modelAndView;
    }
}
