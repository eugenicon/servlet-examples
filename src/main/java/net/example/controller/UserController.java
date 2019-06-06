package net.example.controller;

import net.example.data.model.User;
import net.example.data.validation.Valid;
import net.example.data.validation.ValidationException;
import net.example.resolver.Component;
import net.example.service.GroupService;
import net.example.service.ServiceException;
import net.example.service.UserService;
import net.example.resolver.ExceptionMapping;
import net.example.resolver.GetMapping;
import net.example.resolver.PostMapping;
import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

import java.util.Date;

@Component
public class UserController implements Controller {
    private final UserService userService;
    private final GroupService groupService;

    public UserController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping("/user-list-regular")
    public ModelAndView getUserList() {
        ModelAndView view = new ModelAndView("list-sample.jsp");
        view.addParameter("currentDateFromBackend", new Date());
        view.addParameter("listOfData", userService.getAllUsers());
        return view;
    }

    @GetMapping("/user/list")
    public View getUserListBootstrap() {
        ModelAndView view = getUserList();
        view.setPageUrl("user/user-list.jsp");
        return view;
    }

    @GetMapping("/add-user-regular")
    public View showAddUserPage() {
        return new ModelAndView("add-user.jsp");
    }

    @GetMapping("/user/add")
    public View showAddUserPageBootstrap() {
        ModelAndView modelAndView = new ModelAndView("user/add-user.jsp");
        modelAndView.addParameter("groups", groupService.getAllGroups());
        return modelAndView;
    }

    @GetMapping("/user/edit/{id}")
    public View showEditUserPage(Integer id) throws ServiceException  {
        View modelAndView = showAddUserPageBootstrap();
        modelAndView.addParameter("user", userService.getById(id));
        return modelAndView;
    }

    @PostMapping("/user/save")
    public View saveUser(@Valid User user) throws ServiceException {
        if (user.getId() == 0) {
            userService.addUser(user);
        } else {
            userService.updateUser(user);
        }
        return new RedirectView(new ModelAndView("user/list"));
    }

    @PostMapping("/user/delete/{id}")
    public View deleteUser(Integer id) {
        userService.delete(id);
        return new RedirectView(new ModelAndView("user/list"));
    }

    @ExceptionMapping(ServiceException.class)
    public View showUserAddErrors(ServiceException error) {
        return showUserAddErrors(error.getCause() == null ? error.getMessage() : error.getCause().getMessage(), null);
    }

    @ExceptionMapping(ValidationException.class)
    public View showUserAddErrors(ValidationException error, User user) {
        return showUserAddErrors(String.join("\n", error.getErrors()), user);
    }

    private View showUserAddErrors(String error, User user) {
        View view = new ModelAndView("user/add");
        view.addParameter("error", error);
        view.addParameter("user", user);
        return new RedirectView(view);
    }
}