package net.example.controller;

import net.example.data.model.Group;
import net.example.data.validation.Valid;
import net.example.data.validation.ValidationException;
import net.example.service.GroupService;
import net.example.servlet.ExceptionMapping;
import net.example.servlet.GetMapping;
import net.example.servlet.PostMapping;
import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

public class GroupController {
    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/group-list")
    public ModelAndView getAll() {
        ModelAndView view = new ModelAndView("WEB-INF/jsp/groups-page.jsp");
        view.addParameter("listOfData", groupService.getAllGroups());
        return view;
    }

    @GetMapping("/add-group")
    public ModelAndView addGroup() {
        return new ModelAndView("WEB-INF/jsp/add-group.jsp");
    }

    @PostMapping("/add-group")
    public View addUser(@Valid Group group) {
        groupService.save(group);
        return new RedirectView(new ModelAndView("group-list"));
    }

    @ExceptionMapping(ValidationException.class)
    public View onValidationException(ValidationException e) {
        View view = new ModelAndView("add-group");
        view.addParameter("error", String.join("\n", e.getErrors()));
        return new RedirectView(view);
    }
}
