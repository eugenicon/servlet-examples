package net.example.controller;

import net.example.data.model.Group;
import net.example.data.validation.Valid;
import net.example.data.validation.ValidationException;
import net.example.resolver.Component;
import net.example.resolver.ExceptionMapping;
import net.example.resolver.GetMapping;
import net.example.resolver.PostMapping;
import net.example.service.GroupService;
import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

@Component
public class GroupController implements Controller {
    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/group/list")
    public ModelAndView getAll() {
        ModelAndView view = new ModelAndView("group/groups-page.jsp");
        view.addParameter("listOfData", groupService.getAllGroups());
        return view;
    }

    @GetMapping("/group/add")
    public ModelAndView addGroup() {
        return new ModelAndView("group/add-group.jsp");
    }

    @PostMapping("/group/save")
    public View saveGroup(@Valid Group group) {
        if (group.getId() == 0) {
            groupService.add(group);
        } else {
            groupService.update(group);
        }
        return new RedirectView(new ModelAndView("group/list"));
    }

    @GetMapping("/group/edit/{id}")
    public View editGroup(Integer id) {
        View modelAndView = addGroup();
        modelAndView.addParameter("group", groupService.getById(id));
        return modelAndView;
    }

    @PostMapping("/group/delete/{id}")
    public View deleteGroup(Integer id) {
        groupService.delete(id);
        return new RedirectView(new ModelAndView("group/list"));
    }

    @ExceptionMapping(ValidationException.class)
    public View onValidationException(ValidationException error, Group group) {
        View view = new ModelAndView("group/add");
        view.addParameter("error", String.join("\n", error.getErrors()));
        view.addParameter("group", group);
        return new RedirectView(view);
    }
}
