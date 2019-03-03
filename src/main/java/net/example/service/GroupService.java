package net.example.service;

import net.example.data.dao.GroupDao;
import net.example.data.model.Group;

import java.util.List;

public class GroupService {
    private GroupDao groupDao;

    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public List<Group> getAllGroups() {
        return groupDao.getAll();
    }

    public Group getById(int id) {
        return groupDao.getById(id);
    }
}
