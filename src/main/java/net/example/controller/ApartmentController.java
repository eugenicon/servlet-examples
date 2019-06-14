package net.example.controller;

import net.example.data.model.Apartment;
import net.example.data.model.ApartmentType;
import net.example.resolver.Component;
import net.example.resolver.GetMapping;
import net.example.resolver.PostMapping;
import net.example.service.*;
import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

@Component
public class ApartmentController implements Controller {
    private final ApartmentService apartmentService;
    private final UserService userService;
    private final FileDataService fileDataService;
    private final FacilityService facilityService;

    public ApartmentController(ApartmentService apartmentService, UserService userService, FileDataService fileDataService, FacilityService facilityService) {
        this.apartmentService = apartmentService;
        this.userService = userService;
        this.fileDataService = fileDataService;
        this.facilityService = facilityService;
    }

    @GetMapping("/apartments/list")
    public View listPage() {
        ModelAndView modelAndView = new ModelAndView("apartment/apartment-list.jsp");
        modelAndView.addParameter("listOfData", apartmentService.getAll());
        return modelAndView;
    }

    @GetMapping("/apartments/add")
    public View addPage() {
        ModelAndView view = new ModelAndView("apartment/apartment-add.jsp");
        view.addParameter("types", ApartmentType.values());
        view.addParameter("images", fileDataService.getAll());
        view.addParameter("facilities", facilityService.getAll());
        return view;
    }

    @PostMapping("/apartments/save")
    public View save(Apartment data, AuthenticatedUser auth) {
        if (data.getOwner() == null) {
            data.setOwner(userService.findByName(auth.getName()).orElse(null));
        }
        apartmentService.save(data);
        return new RedirectView(new ModelAndView("apartments/list"));
    }

    @PostMapping("/apartments/delete/{id}")
    public View delete(Integer id) {
        apartmentService.delete(id);
        return new RedirectView(new ModelAndView("apartments/list"));
    }

    @GetMapping("/apartments/edit/{id}")
    public View edit(Integer id) {
        View modelAndView = addPage();
        modelAndView.addParameter("data", apartmentService.getById(id));
        return modelAndView;
    }

    @GetMapping("/apartments/view/{id}")
    public View view(Integer id) {
        ModelAndView view = new ModelAndView("apartment/apartment-view.jsp");
        view.addParameter("data", apartmentService.getById(id));
        return view;
    }

    @GetMapping("/apartments/find")
    public View find(Double priceMin, Double priceMax, Integer numberOfPlaces, ApartmentType type) {
        return listPage();
    }
}
