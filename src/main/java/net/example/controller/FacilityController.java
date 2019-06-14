package net.example.controller;

import net.example.data.model.Facility;
import net.example.data.validation.Valid;
import net.example.data.validation.ValidationException;
import net.example.resolver.Component;
import net.example.resolver.ExceptionMapping;
import net.example.resolver.GetMapping;
import net.example.resolver.PostMapping;
import net.example.service.FacilityService;
import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

@Component
public class FacilityController implements Controller {
    private FacilityService facilityService;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping("/facility/list")
    public ModelAndView getAll() {
        ModelAndView view = new ModelAndView("facility/facility-list.jsp");
        view.addParameter("listOfData", facilityService.getAll());
        return view;
    }

    @GetMapping("/facility/add")
    public ModelAndView add() {
        return new ModelAndView("facility/facility-add.jsp");
    }

    @PostMapping("/facility/save")
    public View save(@Valid Facility entity) {
        facilityService.save(entity);
        return new RedirectView(new ModelAndView("facility/list"));
    }

    @GetMapping("/facility/edit/{id}")
    public View edit(Integer id) {
        View modelAndView = add();
        modelAndView.addParameter("data", facilityService.getById(id));
        return modelAndView;
    }

    @PostMapping("/facility/delete/{id}")
    public View delete(Integer id) {
        facilityService.delete(id);
        return new RedirectView(new ModelAndView("facility/list"));
    }

    @ExceptionMapping(ValidationException.class)
    public View onValidationException(ValidationException error, Facility entity) {
        View view = new ModelAndView("facility/add");
        view.addParameter("error", String.join("\n", error.getErrors()));
        view.addParameter("data", entity);
        return new RedirectView(view);
    }
}
