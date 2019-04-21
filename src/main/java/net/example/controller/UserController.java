package net.example.controller;

import net.example.data.model.User;
import net.example.data.validation.ValidationService;
import net.example.service.GroupService;
import net.example.service.ServiceException;
import net.example.service.UserService;
import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

import java.util.Date;
import java.util.List;

public class UserController {
    private final UserService userService;
    private final GroupService groupService;
    private ValidationService validationService;

    public UserController(UserService userService, GroupService groupService, ValidationService validationService) {
        this.userService = userService;
        this.groupService = groupService;
        this.validationService = validationService;
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
        List<String> validationErrors = validationService.validate(user);
        if (validationErrors.isEmpty()) {
            try {
                userService.addUser(user);
                view = new ModelAndView("user-list");
            } catch (ServiceException e) {
                view = showUserAddErrors(e.getCause() == null ? e.getMessage() : e.getCause().getMessage());
            }
        } else {
            view = showUserAddErrors(String.join("\n", validationErrors));
        }
        return new RedirectView(view);
    }

    private View showUserAddErrors(String error) {
        View view = new ModelAndView("add-user-bootstrap");
        view.addParameter("error", error);
        return view;
    }
}
