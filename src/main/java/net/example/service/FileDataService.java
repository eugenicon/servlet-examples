package net.example.service;

import net.example.data.dao.FileDataDao;
import net.example.data.model.FileData;
import net.example.resolver.Component;

import java.util.List;

@Component
public class FileDataService {
    private FileDataDao fileDataDao;

    public FileDataService(FileDataDao fileDataDao) {
        this.fileDataDao = fileDataDao;
    }

    public List<FileData> getAll() {
        return fileDataDao.getAll();
    }

    public FileData getById(int id) {
        return fileDataDao.getById(id);
    }

    public void delete(Integer id) {
        fileDataDao.delete(id);
    }

    public void save(FileData fileData) {
        if (fileData.getId() == 0) {
            fileDataDao.add(fileData);
        } else {
            fileDataDao.update(fileData);
        }
    }
}
