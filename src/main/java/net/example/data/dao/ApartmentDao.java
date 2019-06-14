package net.example.data.dao;

import net.example.data.model.Apartment;
import net.example.data.model.ApartmentType;
import net.example.resolver.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ApartmentDao {
    private final DataSource dataSource;
    private final UserDao userDao;
    private final FileDataDao fileDataDao;
    private final FacilityDao facilityDao;

    public ApartmentDao(DataSource dataSource, UserDao userDao, FileDataDao fileDataDao, FacilityDao facilityDao) {
        this.dataSource = dataSource;
        this.userDao = userDao;
        this.fileDataDao = fileDataDao;
        this.facilityDao = facilityDao;
    }

    private Apartment convert(ResultSet rs) throws SQLException {
        Apartment entity = new Apartment();
        entity.setId(rs.getInt("id"));
        entity.setName(rs.getString("name"));
        entity.setNumberOfPlaces(rs.getInt("numberOfPlaces"));
        entity.setDescription(rs.getString("description"));
        entity.setAddress(rs.getString("address"));
        entity.setType(ApartmentType.valueOf(rs.getString("type")));
        entity.setOwner(userDao.getById(rs.getInt("owner")).orElse(null));
        entity.setImages(fileDataDao.getAllByOwner(entity.getId()));
        entity.setFacilities(facilityDao.getAllByApartment(entity.getId()));
        return entity;
    }

    private void prepare(Apartment entity, PreparedStatement ps, boolean isUpdate) throws SQLException {
        ps.setString(1, entity.getName());
        ps.setInt(2, entity.getNumberOfPlaces());
        ps.setString(3, entity.getDescription());
        ps.setString(4, entity.getAddress());
        ps.setString(5, entity.getType().name());
        ps.setInt(6, entity.getOwner().getId());
        if (isUpdate) {
            ps.setInt(7, entity.getId());
        }
    }

    public List<Apartment> getAll() {
        return dataSource.query("select * from apartments order by id").list(this::convert);
    }

    public void add(Apartment entity) {
        dataSource.query("insert into apartments (name,numberOfPlaces,description,address,type,owner) values(?,?,?,?,?,?)")
                .prepare(ps -> prepare(entity, ps, false))
                .execute(rs -> entity.setId(rs.getInt(1)));
    }

    public Apartment getById(int entityId) {
        return dataSource.query("select * from apartments where id = ?")
                .prepare(ps -> ps.setInt(1, entityId))
                .first(this::convert)
                .orElse(null);
    }

    public void delete(int entityId) {
        dataSource.query("delete from linked_files where owner_id = ?")
                .prepare(ps -> ps.setInt(1, entityId))
                .and("delete from apartments where id = ?")
                .prepare(ps -> ps.setInt(1, entityId))
                .execute();
    }

    public void update(Apartment entity) {
        dataSource.query("update apartments set name = ?, numberOfPlaces = ?, description = ?, address = ?, type = ?, owner = ? where id = ?")
                .prepare(ps -> prepare(entity, ps, true))
                .execute();
    }
}
