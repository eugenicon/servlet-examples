package net.example.data.dao;

import net.example.data.model.Group;
import net.example.resolver.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class GroupDao {
    private DataSource dataSource;

    public GroupDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Group convert(ResultSet rs) throws SQLException {
        Group group = new Group();
        group.setName(rs.getString("name"));
        group.setId(rs.getInt("id"));
        return group;
    }

    public List<Group> getAll() {
        return dataSource.query("select * from groups").list(this::convert);
    }

    public void save(Group group) {
        dataSource.query("insert into groups (name) values(?)")
                .prepare(ps -> ps.setString(1, group.getName()))
                .execute(rs -> group.setId(rs.getInt(1)));
    }

    public Group getById(int groupId) {
        return dataSource.query("select * from groups where id = ?")
                .prepare(ps -> ps.setInt(1, groupId))
                .first(this::convert)
                .orElse(null);
    }

    public void delete(int groupId) {
        dataSource.query("delete from users where group_id = ?")
                .prepare(ps -> ps.setInt(1, groupId))
                .and("delete from groups where id = ?")
                .prepare(ps -> ps.setInt(1, groupId))
                .execute();
    }
}
