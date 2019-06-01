package net.example.service;

import net.example.data.dao.GroupDao;
import net.example.data.model.Group;
import net.example.resolver.Component;

import java.util.List;

@Component
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

    public void save(Group group) {
        groupDao.save(group);
    }
}
