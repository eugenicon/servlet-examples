package net.example.data.dao;

import net.example.data.model.Group;
import net.example.data.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {
    private DataSource dataSource;

    public GroupDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Group> getAll() {
        List<Group> groups = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement("select * from groups");
                ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {
                Group user = new Group();
                user.setName(rs.getString("name"));
                user.setId(rs.getInt("id"));
                groups.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return groups;
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
