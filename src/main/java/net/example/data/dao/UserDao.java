package net.example.data.dao;

import net.example.data.model.Group;
import net.example.data.model.User;

import java.util.List;

public class UserDao {
    private DataSource dataSource;
    private GroupDao groupDao;

    public UserDao(DataSource dataSource, GroupDao groupDao) {
        this.dataSource = dataSource;
        this.groupDao = groupDao;
    }

    public List<User> getAll() {
        return dataSource.executeQuery("select name, age, group_id from users", rs -> {
            User user = new User();
            user.setName(rs.getString("name"));
            user.setAge(rs.getInt("age"));
            user.setGroup(groupDao.getById(rs.getInt("group_id")));
            return user;
        });
    }

    public void save(User user) throws Exception {
        if (user != null) {

        }
        throw new Exception("Could not save user");
    }

    public List<User> getUsersByGroup(Group group) {
        return null;
    }
}
