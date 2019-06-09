package net.example.data.dao;

import net.example.data.model.FileData;
import net.example.resolver.Component;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<FileData> getAllByOwner(int id) {
        return dataSource.query("select f.* from files f left join linked_files lf on f.id = lf.file_id where lf.owner_id = ?")
                .prepare(ps -> ps.setInt(1, id))
                .list(this::convert);
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

    public void delete(int entityId) {
        dataSource.query("delete from linked_files where file_id = ?")
                .prepare(ps -> ps.setInt(1, entityId))
                .and("delete from files where id = ?")
                .prepare(ps -> ps.setInt(1, entityId))
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

    public void saveLinks(List<FileData> fileData, Integer ownerId) {
        dataSource.query("delete from linked_files where owner_id = ?")
                .prepare(ps -> ps.setInt(1, ownerId))
                .and("insert into linked_files (owner_id, file_id) values(?,?)")
                .prepare(fileData, (ps, file) -> {
                    ps.setInt(1, ownerId);
                    ps.setInt(2, file.getId());
                })
                .execute();
    }

    public List<FileData> getAllById(List<Integer> imageIds) {
        return dataSource.query("select * from files where id = ANY (?)")
                .prepare(ps -> {
                    Array array = ps.getConnection().createArrayOf("int", imageIds.toArray());
                    ps.setArray(1, array);
                })
                .list(this::convert);
    }
}
