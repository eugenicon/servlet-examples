package net.example.controller;

import net.example.data.model.User;
import net.example.service.GroupService;
import net.example.service.ServiceException;
import net.example.service.UserService;
import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

import java.util.Date;

public class UserController {
    private final UserService userService;
    private final GroupService groupService;

    public UserController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    public ModelAndView getUserList() {
        ModelAndView view = new ModelAndView("WEB-INF/jsp/list-sample.jsp");
        view.addParameter("currentDateFromBackend", new Date());
        view.addParameter("listOfData", userService.getAllUsers());
        return view;
    }

    public View getUserListBootstrap() {
        ModelAndView view = getUserList();
        view.setPageUrl("WEB-INF/jsp/bootstrap-list-sample.jsp");
        return view;
    }

    public View showAddUserPage() {
        return new ModelAndView("WEB-INF/jsp/add-user.jsp");
    }

    public View showAddUserPageBootstrap() {
        ModelAndView modelAndView = new ModelAndView("WEB-INF/jsp/add-user-bootstrap.jsp");
        modelAndView.addParameter("groups", groupService.getAllGroups());
        return modelAndView;
    }

    public View addUser(User user) {
        View view;
        try {
            userService.addUser(user);
            view = new ModelAndView("user-list");
        } catch (ServiceException e) {
            view = new ModelAndView("add-user");
            view.addParameter("error", e.getCause() == null ? e.getMessage() : e.getCause().getMessage());
        }
        return new RedirectView(view);
    }
}
