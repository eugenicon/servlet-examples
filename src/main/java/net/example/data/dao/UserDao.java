package net.example.data.dao;

import net.example.data.model.Group;
import net.example.data.model.User;

import java.util.List;

public class UserDao {
    private DataSource dataSource;
    private DataSource.SqlFunction<User> converter;

    public UserDao(DataSource dataSource, GroupDao groupDao) {
        this.dataSource = dataSource;

        converter = rs -> {
            User user = new User();
            user.setName(rs.getString("name"));
            user.setAge(rs.getInt("age"));
            user.setGroup(groupDao.getById(rs.getInt("group_id")));
            return user;
        };
    }

    public List<User> getAll() {
        return dataSource.selectQuery("select name, age, group_id from users", converter);
    }

    public void save(User user) throws Exception {
        if (user != null) {

        }
        throw new Exception("Could not save user");
    }

    public List<User> getUsersByGroup(Group group) {
        return dataSource.selectQuery("select name, age, group_id from users where group_id = ?", converter, ps -> ps.setInt(1, group.getId()));
    }
}
