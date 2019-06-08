package net.example.controller;

import net.example.data.dto.UserRegistrationDto;
import net.example.data.model.Role;
import net.example.data.model.User;
import net.example.data.validation.Valid;
import net.example.data.validation.ValidationException;
import net.example.resolver.Component;
import net.example.resolver.ExceptionMapping;
import net.example.resolver.GetMapping;
import net.example.resolver.PostMapping;
import net.example.service.ServiceException;
import net.example.service.UserService;
import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

import javax.servlet.http.HttpSession;

@Component
public class RegistrationController implements Controller {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public View register() {
        return new ModelAndView("login/register.jsp");
    }

    @PostMapping("/register")
    public View register(@Valid UserRegistrationDto userLogin, HttpSession session) {
        User user = new User();
        user.setName(userLogin.getUserName());
        user.setPassword(userLogin.getPassword());
        user.setRole(Role.USER);
        userService.save(user);
        return new RedirectView(new ModelAndView("login"));
    }

    @ExceptionMapping(ServiceException.class)
    public View showUserAddErrors(ServiceException error, UserRegistrationDto userLogin, String refererUrl) {
        ModelAndView modelAndView = new ModelAndView(refererUrl);
        modelAndView.addParameter("login", userLogin);
        return new RedirectView(modelAndView);
    }

    @ExceptionMapping(ValidationException.class)
    public View showUserAddErrors(ValidationException error, UserRegistrationDto userLogin, String refererUrl) {
        ModelAndView modelAndView = new ModelAndView(refererUrl);
        modelAndView.addParameter("login", userLogin);
        return new RedirectView(modelAndView);
    }
}
