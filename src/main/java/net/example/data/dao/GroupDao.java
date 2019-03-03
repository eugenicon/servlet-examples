package net.example.data.dao;

import net.example.data.model.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GroupDao {
    private DataSource dataSource;

    public GroupDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Group> getAll() {
        return dataSource.executeQuery("select * from groups", rs -> {
            Group group = new Group();
            group.setName(rs.getString("name"));
            group.setId(rs.getInt("id"));
            return group;
        });
    }

    public void save(Group group) {

    }

    public Group getById(int groupId) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement("select * from groups where id = ?");

        ) {
            ps.setInt(1, groupId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Group group = new Group();
                    group.setId(rs.getInt("id"));
                    group.setName(rs.getString("name"));
                    return group;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
