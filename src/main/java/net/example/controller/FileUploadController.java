package net.example.controller;

import net.example.data.model.FileData;
import net.example.resolver.Component;
import net.example.resolver.GetMapping;
import net.example.resolver.PostMapping;
import net.example.service.FileDataService;
import net.example.view.ModelAndView;
import net.example.view.RedirectView;
import net.example.view.View;

@Component
public class FileUploadController implements Controller {
    private final FileDataService fileDataService;

    public FileUploadController(FileDataService fileDataService) {
        this.fileDataService = fileDataService;
    }

    @GetMapping("/files/list")
    public View listPage() {
        ModelAndView modelAndView = new ModelAndView("files/file-list.jsp");
        modelAndView.addParameter("listOfData", fileDataService.getAll());
        return modelAndView;
    }

    @GetMapping("/files/add")
    public View addPage() {
        return new ModelAndView("files/file-add.jsp");
    }

    @PostMapping("/files/save")
    public View save(FileData fileData) {
        fileDataService.save(fileData);
        return new RedirectView(new ModelAndView("files/list"));
    }

    @PostMapping("/files/delete/{id}")
    public View delete(Integer id) {
        fileDataService.delete(id);
        return new RedirectView(new ModelAndView("files/list"));
    }

    @GetMapping("/files/edit/{id}")
    public View edit(Integer id) {
        View modelAndView = addPage();
        modelAndView.addParameter("file", fileDataService.getById(id));
        return modelAndView;
    }
}
