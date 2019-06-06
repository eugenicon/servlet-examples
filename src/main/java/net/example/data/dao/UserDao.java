package net.example.data.dao;

import net.example.data.model.Group;
import net.example.data.model.Role;
import net.example.data.model.User;
import net.example.resolver.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserDao {
    private DataSource dataSource;
    private GroupDao groupDao;

    public UserDao(DataSource dataSource, GroupDao groupDao) {
        this.dataSource = dataSource;
        this.groupDao = groupDao;
    }

    private User convert(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setAge(rs.getInt("age"));
        user.setGroup(groupDao.getById(rs.getInt("group_id")));
        user.setPassword(rs.getString("password"));
        String roleName = rs.getString("role");
        user.setRole(Arrays.stream(Role.values())
                .filter(v -> v.name().equalsIgnoreCase(roleName))
                .findFirst().orElse(Role.USER)
        );
        return user;
    }

    private void prepare(PreparedStatement ps, User user) throws SQLException {
        ps.setString(1, user.getName());
        ps.setInt(2, user.getAge());
        ps.setInt(3, user.getGroup() != null ? user.getGroup().getId() : 0);
        ps.setString(4, user.getRole() != null ? user.getRole().name() : Role.USER.name());
        ps.setString(5, user.getPassword());
    }

    public List<User> getAll() {
        return dataSource.query("select * from users").list(this::convert);
    }

    public void save(User user) {
        dataSource.query("insert into users (name, age, group_id, role, password) values(?, ?, ?, ?, ?)")
                .prepare(ps -> prepare(ps, user))
                .execute(rs -> user.setId(rs.getInt(1)));
    }

    public List<User> getUsersByGroup(Group group) {
        return dataSource.query("select * from users where group_id = ?")
                .prepare(ps -> ps.setInt(1, group.getId()))
                .list(this::convert);
    }

    public Optional<User> getById(Integer id) {
        return dataSource.query("select * from users where id = ?")
                .prepare(ps -> ps.setInt(1, id))
                .first(this::convert);
    }

    public Optional<User> findByName(String userName) {
        return dataSource.query("select * from users where name = ?")
                .prepare(ps -> ps.setString(1, userName))
                .first(this::convert);
    }

    public void saveAll(List<User> users) {
        dataSource.query("insert into users (name, age, group_id, role, password) values(?, ?, ?, ?, ?)")
                .prepare(users, this::prepare)
                .execute();
    }
}
