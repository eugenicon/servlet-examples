package net.example.service;

import net.example.data.dao.FacilityDao;
import net.example.data.model.Apartment;
import net.example.data.model.Facility;
import net.example.resolver.Component;

import java.util.List;

@Component
public class FacilityService {
    private FacilityDao facilityDao;

    public FacilityService(FacilityDao facilityDao) {
        this.facilityDao = facilityDao;
    }

    public List<Facility> getAll() {
        return facilityDao.getAll();
    }

    public Facility getById(int id) {
        return facilityDao.getById(id);
    }

    public void delete(Integer id) {
        facilityDao.delete(id);
    }

    public void save(Facility entity) {
        if (entity.getId() == 0) {
            facilityDao.add(entity);
        } else {
            facilityDao.update(entity);
        }
    }

    public List<Facility> getAllByApartment(Apartment apartment) {
        return facilityDao.getAllByApartment(apartment.getId());
    }

    public List<Facility> getAllById(List<Integer> entitiesId) {
        return facilityDao.getAllById(entitiesId);
    }

    public void saveForApartment(List<Facility> facilities, Apartment apartment) {
        if (!facilities.isEmpty()) {
            facilityDao.saveForApartment(facilities, apartment.getId());
        }
    }
}
