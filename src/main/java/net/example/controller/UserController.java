package net.example.controller;

import net.example.data.model.User;
import net.example.data.validation.ValidationException;
import net.example.data.validation.ValidationService;
import net.example.service.GroupService;
import net.example.service.ServiceException;
import net.example.service.UserService;
import net.example.servlet.ExceptionMapping;
import net.example.servlet.GetMapping;
import net.example.servlet.PostMapping;
import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

import java.util.Date;
import java.util.List;

public class UserController {
    private final UserService userService;
    private final GroupService groupService;
    private final ValidationService validationService;

    public UserController(UserService userService, GroupService groupService, ValidationService validationService) {
        this.userService = userService;
        this.groupService = groupService;
        this.validationService = validationService;
    }

    @GetMapping("/user-list")
    public ModelAndView getUserList() {
        ModelAndView view = new ModelAndView("WEB-INF/jsp/list-sample.jsp");
        view.addParameter("currentDateFromBackend", new Date());
        view.addParameter("listOfData", userService.getAllUsers());
        return view;
    }

    @GetMapping("/user-list-bootstrap")
    public View getUserListBootstrap() {
        ModelAndView view = getUserList();
        view.setPageUrl("WEB-INF/jsp/bootstrap-list-sample.jsp");
        return view;
    }

    @GetMapping("/add-user")
    public View showAddUserPage() {
        return new ModelAndView("WEB-INF/jsp/add-user.jsp");
    }

    @GetMapping("/add-user-bootstrap")
    public View showAddUserPageBootstrap() {
        ModelAndView modelAndView = new ModelAndView("WEB-INF/jsp/add-user-bootstrap.jsp");
        modelAndView.addParameter("groups", groupService.getAllGroups());
        return modelAndView;
    }

    @PostMapping("/add-user")
    public View addUser(User user) throws ServiceException, ValidationException {
        View view;
        List<String> validationErrors = validationService.validate(user);
        if (validationErrors.isEmpty()) {
            userService.addUser(user);
            view = new ModelAndView("user-list");
        } else {
            throw new ValidationException(validationErrors);
        }
        return new RedirectView(view);
    }

    @ExceptionMapping(ServiceException.class)
    public View showUserAddErrors(ServiceException e) {
        return showUserAddErrors(e.getCause() == null ? e.getMessage() : e.getCause().getMessage());
    }

    @ExceptionMapping(ValidationException.class)
    public View showUserAddErrors(ValidationException e) {
        return showUserAddErrors(String.join("\n", e.getErrors()));
    }

    private View showUserAddErrors(String error) {
        View view = new ModelAndView("add-user-bootstrap");
        view.addParameter("error", error);
        return new RedirectView(view);
    }
}
