package net.example.data.dao;

import net.example.data.model.Group;

import java.util.List;

public class GroupDao {
    private DataSource dataSource;
    private DataSource.SqlFunction<Group> converter;

    public GroupDao(DataSource dataSource) {
        this.dataSource = dataSource;

        converter = rs -> {
            Group group = new Group();
            group.setName(rs.getString("name"));
            group.setId(rs.getInt("id"));
            return group;
        };
    }

    public List<Group> getAll() {
        return dataSource.selectQuery("select * from groups", converter);
    }

    public void save(Group group) {

    }

    public Group getById(int groupId) {
        return dataSource.selectFirst("select * from groups where id = ?", converter, ps -> ps.setInt(1, groupId))
                .orElse(null);
    }
}
