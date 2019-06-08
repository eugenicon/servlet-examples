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

    public void delete(Integer id) {
        groupDao.delete(id);
    }

    public void save(Group group) {
        if (group.getId() == 0) {
            groupDao.add(group);
        } else {
            groupDao.update(group);
        }
    }
}
