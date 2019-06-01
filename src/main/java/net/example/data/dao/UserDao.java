package net.example.data.dao;

import net.example.data.model.Group;
import net.example.data.model.User;
import net.example.resolver.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDao {
    private DataSource dataSource;
    private DataSource.SqlFunction<User> converter;

    public UserDao(DataSource dataSource, GroupDao groupDao) {
        this.dataSource = dataSource;

        converter = rs -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setAge(rs.getInt("age"));
            user.setGroup(groupDao.getById(rs.getInt("group_id")));
            return user;
        };
    }

    public List<User> getAll() {
        return dataSource.selectQuery("select id, name, age, group_id from users", converter);
    }

    public void save(User user) {
        dataSource.executeUpdate("insert into users (name, age, group_id) values(?, ?, ?)", ps -> {
            ps.setString(1, user.getName());
            ps.setInt(2, user.getAge());
            ps.setInt(3, user.getGroup() != null ? user.getGroup().getId() : 0);
        }, rs -> user.setId(rs.getInt(1)));
    }

    public List<User> getUsersByGroup(Group group) {
        return dataSource.selectQuery("select name, age, group_id from users where group_id = ?", converter, ps -> ps.setInt(1, group.getId()));
    }

    public Optional<User> getById(Integer id) {
        return dataSource.selectFirst("select id, name, age, group_id from users where id = ?", converter, ps -> ps.setInt(1, id));
    }
}
