package net.example.data.dao;

import net.example.data.model.Group;
import net.example.data.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private DataSource dataSource;
    private GroupDao groupDao;

    public UserDao(DataSource dataSource, GroupDao groupDao) {
        this.dataSource = dataSource;
        this.groupDao = groupDao;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement("select name, age, group_id from users");
                ResultSet rs = ps.executeQuery();
                ) {

            while (rs.next()) {
                User user = new User();
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
                user.setGroup(groupDao.getById(rs.getInt("group_id")));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
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
