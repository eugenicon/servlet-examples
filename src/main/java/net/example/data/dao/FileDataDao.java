package net.example.data.dao;

import net.example.data.model.FileData;
import net.example.resolver.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@Component
public class FileDataDao {
    private DataSource dataSource;

    public FileDataDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private FileData convert(ResultSet rs) throws SQLException {
        FileData fileData = new FileData();
        fileData.setName(rs.getString("name"));
        fileData.setId(rs.getInt("id"));
        fileData.setData(rs.getBytes("data"));
        fileData.setSize(rs.getLong("size"));
        fileData.setEncoded(Base64.getEncoder().encodeToString(fileData.getData()));
        return fileData;
    }

    public List<FileData> getAll() {
        return dataSource.query("select * from files order by id").list(this::convert);
    }

    public void add(FileData fileData) {
        dataSource.query("insert into files (name, data, size) values(?,?,?)")
                .prepare(ps -> {
                    ps.setString(1, fileData.getName());
                    ps.setBytes(2, fileData.getData());
                    ps.setLong(3, fileData.getSize());
                })
                .execute(rs -> fileData.setId(rs.getInt(1)));
    }

    public FileData getById(int id) {
        return dataSource.query("select * from files where id = ?")
                .prepare(ps -> ps.setInt(1, id))
                .first(this::convert)
                .orElse(null);
    }

    public void delete(int groupId) {
        dataSource.query("delete from files where id = ?")
                .prepare(ps -> ps.setInt(1, groupId))
                .execute();
    }

    public void update(FileData fileData) {
        dataSource.query("update files set name = ?, data = ?, size = ? where id = ?")
                .prepare(ps -> {
                    ps.setString(1, fileData.getName());
                    ps.setBytes(2, fileData.getData());
                    ps.setLong(3, fileData.getSize());
                    ps.setInt(4, fileData.getId());
                })
                .execute();
    }
}
