package net.example.data.dao;

import net.example.data.model.Facility;
import net.example.resolver.Component;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FacilityDao {
    private DataSource dataSource;

    public FacilityDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Facility convert(ResultSet rs) throws SQLException {
        Facility entity = new Facility();
        entity.setName(rs.getString("name"));
        entity.setId(rs.getInt("id"));
        return entity;
    }

    public List<Facility> getAll() {
        return dataSource.query("select * from facilities order by id").list(this::convert);
    }

    public void add(Facility entity) {
        dataSource.query("insert into facilities (name) values(?)")
                .prepare(ps -> ps.setString(1, entity.getName()))
                .execute(rs -> entity.setId(rs.getInt(1)));
    }

    public Facility getById(int entityId) {
        return dataSource.query("select * from facilities where id = ?")
                .prepare(ps -> ps.setInt(1, entityId))
                .first(this::convert)
                .orElse(null);
    }

    public void delete(int entityId) {
        dataSource.query("delete from apartment_facilities where facility_id = ?")
                .prepare(ps -> ps.setInt(1, entityId))
                .and("delete from facilities where id = ?")
                .prepare(ps -> ps.setInt(1, entityId))
                .execute();
    }

    public void update(Facility entity) {
        dataSource.query("update facilities set name = ? where id = ?")
                .prepare(ps -> {
                    ps.setString(1, entity.getName());
                    ps.setInt(2, entity.getId());
                })
                .execute();
    }

    public List<Facility> getAllByApartment(int apartmentId) {
        return dataSource.query("select f.* from facilities f left join apartment_facilities af on f.id = af.facility_id where af.apartment_id = ?")
                .prepare(ps -> ps.setInt(1, apartmentId))
                .list(this::convert);
    }

    public List<Facility> getAllById(List<Integer> entitiesId) {
        return dataSource.query("select * from facilities where id = ANY (?)")
                .prepare(ps -> {
                    Array array = ps.getConnection().createArrayOf("int", entitiesId.toArray());
                    ps.setArray(1, array);
                })
                .list(this::convert);
    }

    public void saveForApartment(List<Facility> facilities, int apartmentId) {
        dataSource.query("delete from apartment_facilities where apartment_id = ?")
                .prepare(ps -> ps.setInt(1, apartmentId))
                .and("insert into apartment_facilities (facility_id, apartment_id) values(?,?)")
                .prepare(facilities, (ps, facility) -> {
                    ps.setInt(1, facility.getId());
                    ps.setInt(2, apartmentId);
                })
                .execute();
    }
}
