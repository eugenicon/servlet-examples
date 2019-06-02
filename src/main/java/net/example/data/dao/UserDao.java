package net.example.data.dao;

import net.example.data.model.Group;
import net.example.data.model.Role;
import net.example.data.model.User;
import net.example.resolver.Component;

import java.util.Arrays;
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
            user.setPassword(rs.getString("password"));
            String roleName = rs.getString("role");
            user.setRole(Arrays.stream(Role.values())
                    .filter(v -> v.name().equalsIgnoreCase(roleName))
                    .findFirst().orElse(Role.USER)
            );
            return user;
        };
    }

    public List<User> getAll() {
        return dataSource.selectQuery("select * from users", converter);
    }

    public void save(User user) {
        dataSource.executeUpdate("insert into users (name, age, group_id, role, password) values(?, ?, ?, ?, ?)", ps -> {
            ps.setString(1, user.getName());
            ps.setInt(2, user.getAge());
            ps.setInt(3, user.getGroup() != null ? user.getGroup().getId() : 0);
            ps.setString(4, user.getRole().name());
            ps.setString(5, user.getPassword());
        }, rs -> user.setId(rs.getInt(1)));
    }

    public List<User> getUsersByGroup(Group group) {
        return dataSource.selectQuery("select * from users where group_id = ?", converter, ps -> ps.setInt(1, group.getId()));
    }

    public Optional<User> getById(Integer id) {
        return dataSource.selectFirst("select * from users where id = ?", converter, ps -> ps.setInt(1, id));
    }

    public Optional<User> findByName(String userName) {
        return dataSource.selectFirst("select * from users where name = ?", converter, ps -> ps.setString(1, userName));
    }
}
