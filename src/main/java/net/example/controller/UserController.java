package net.example.controller;

import net.example.data.User;
import net.example.service.ServiceException;
import net.example.service.UserService;
import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

import java.util.Date;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public ModelAndView getUserList() {
        ModelAndView view = new ModelAndView("WEB-INF/jsp-secured/list-sample.jsp");
        view.addParameter("currentDateFromBackend", new Date());
        view.addParameter("listOfData", userService.getAllUsers());
        return view;
    }

    public View getUserListBootstrap() {
        ModelAndView view = getUserList();
        view.setPageUrl("WEB-INF/jsp-secured/bootstrap-list-sample.jsp");
        return view;
    }

    public View showAddUserPage() {
        return new ModelAndView("WEB-INF/jsp-secured/add-user.jsp");
    }

    public View showAddUserPageBootsrtap() {
        return new ModelAndView("WEB-INF/jsp-secured/add-user-bootstrap.jsp");
    }

    public View addUser(User user) {
        View view;
        try {
            userService.addUser(user);
            view = new ModelAndView("user-list");
        } catch (ServiceException e) {
            view = new ModelAndView("add-user");
            view.addParameter("error", e.getMessage());
        }
        return new RedirectView(view);
    }
}
