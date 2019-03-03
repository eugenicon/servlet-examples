package net.example.controller;

import net.example.service.GroupService;
import net.example.view.ModelAndView;

public class GroupController {
    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    public ModelAndView getAll() {
        ModelAndView view = new ModelAndView("WEB-INF/jsp/groups-page.jsp");
        view.addParameter("listOfData", groupService.getAllGroups());
        return view;
    }
}
