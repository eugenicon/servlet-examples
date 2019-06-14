package net.example.tranforemer;

import net.example.data.model.Apartment;
import net.example.data.model.ApartmentType;
import net.example.resolver.Component;
import net.example.service.FacilityService;
import net.example.service.FileDataService;
import net.example.service.ServiceException;
import net.example.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApartmentTransformer implements Transformer<Apartment> {
    private final UserService userService;
    private final IntegerTransformer integerTransformer;
    private final FileDataService fileDataService;
    private final FacilityService facilityService;

    public ApartmentTransformer(UserService userService, IntegerTransformer integerTransformer, FileDataService fileDataService, FacilityService facilityService) {
        this.userService = userService;
        this.integerTransformer = integerTransformer;
        this.fileDataService = fileDataService;
        this.facilityService = facilityService;
    }

    @Override
    public Apartment transform(HttpServletRequest request, String parameter) throws ServiceException {
        Apartment entity = new Apartment();
        entity.setId(integerTransformer.transform(request, "id"));
        entity.setName(request.getParameter("name"));
        entity.setType(ApartmentType.valueOf(request.getParameter("type")));
        entity.setDescription(request.getParameter("description"));
        entity.setAddress(request.getParameter("address"));
        entity.setNumberOfPlaces(integerTransformer.transform(request, "numberOfPlaces"));
        Integer ownerId = integerTransformer.transform(request, "owner");
        if (ownerId > 0) {
            entity.setOwner(userService.getById(ownerId));
        }
        String[] images = request.getParameterValues("images[]");
        if (images != null) {
            List<Integer> entitiesId = Arrays.stream(images).map(Integer::parseInt).collect(Collectors.toList());
            entity.setImages(fileDataService.getAllById(entitiesId));
        }
        String[] facilities = request.getParameterValues("facilities[]");
        if (facilities != null) {
            List<Integer> entitiesId = Arrays.stream(facilities).map(Integer::parseInt).collect(Collectors.toList());
            entity.setFacilities(facilityService.getAllById(entitiesId));
        }
        return entity;
    }

    @Override
    public Class<Apartment> getSupportedType() {
        return Apartment.class;
    }
}
