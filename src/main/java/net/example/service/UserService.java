package net.example.service;

import net.example.data.dao.UserDao;
import net.example.data.model.Group;
import net.example.data.model.User;
import net.example.resolver.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    public List<User> getUsersInGroup(Group group) {
        return userDao.getUsersByGroup(group);
    }

    public User getById(Integer id) throws ServiceException {
        return userDao.getById(id).orElseThrow(() -> new ServiceException("Could not find user with id " + id));
    }

    public Optional<User> findMatchingCredentials(String userName, String password) {
        return userDao.findByName(userName).map(user -> password.equalsIgnoreCase(user.getPassword()) ? user : null);
    }

    public void delete(Integer id) {
        userDao.delete(id);
    }

    public void save(User user) {
        if (user.getId() == 0) {
            userDao.add(user);
        } else {
            userDao.update(user);
        }
    }
}