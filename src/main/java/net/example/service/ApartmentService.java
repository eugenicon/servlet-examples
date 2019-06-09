package net.example.service;

import net.example.data.dao.ApartmentDao;
import net.example.data.model.Apartment;
import net.example.resolver.Component;

import java.util.List;

@Component
public class ApartmentService {
    private final ApartmentDao apartmentDao;
    private final FileDataService fileDataService;

    public ApartmentService(ApartmentDao apartmentDao, FileDataService fileDataService) {
        this.apartmentDao = apartmentDao;
        this.fileDataService = fileDataService;
    }

    public List<Apartment> getAll() {
        return apartmentDao.getAll();
    }

    public Apartment getById(int id) {
        return apartmentDao.getById(id);
    }

    public void delete(Integer id) {
        apartmentDao.delete(id);
    }

    public void save(Apartment entity) {
        if (entity.getId() == 0) {
            apartmentDao.add(entity);
        } else {
            apartmentDao.update(entity);
        }
        fileDataService.saveLinks(entity.getImages(), entity.getId());
    }
}
