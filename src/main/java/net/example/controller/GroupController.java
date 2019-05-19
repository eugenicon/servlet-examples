package net.example.controller;

import net.example.service.GroupService;
import net.example.servlet.GetMapping;
import net.example.view.ModelAndView;

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
}
