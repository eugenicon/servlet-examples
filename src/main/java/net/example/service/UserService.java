package net.example.service;

import net.example.data.dao.UserDao;
import net.example.data.model.Group;
import net.example.data.model.User;

import java.util.List;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    public void addUser(User user) throws ServiceException {
        try {
            userDao.save(user);
        } catch (Exception e) {
            throw new ServiceException("Could not save user", e);
        }
    }

    public List<User> getUsersInGroup(Group group) {
        return userDao.getUsersByGroup(group);
    }
}
