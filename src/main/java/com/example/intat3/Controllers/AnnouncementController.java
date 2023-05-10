package com.example.intat3.Controllers;


import com.example.intat3.Dto.*;
import com.example.intat3.Entity.Announcement;
import com.example.intat3.services.AnnouncementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://intproj22.sit.kmutt.ac.th:8080/at3")
@RestController
@RequestMapping("/api/announcements")

public class AnnouncementController {
    @Autowired
    private AnnouncementService service;
    @Autowired
    private ModelMapper modelMapper;
    @GetMapping("")
    public List<AllAnnouncementDto> getAllAnnouncement (@RequestParam(defaultValue = "admin") String mode){
        if(mode.equals("admin")) {
            System.out.println("asdasdqweqda");
            return service.getAllAnnouncement();
        }else if(mode.equals("active")){
            return service.getAnnByDisplay(mode);
        }else{
            return service.getAnnByDisplay(mode);
        }
    }

    @GetMapping("/{id}")
    public AnnouncementDto getById(@PathVariable Integer id){
        return service.getAnnouncementById(id);
    }

    @PostMapping("")
    public AnnouncementDto createAnnouncement(@RequestBody UpdateAnnouncementDto ann){
        return service.createAnn(ann);
    }

    @DeleteMapping ("/{id}")
    public void deleteOffice(@PathVariable Integer id){
        service.deleteAnn(id);
    }

    @PutMapping("/{id}")
    public UpdateDTO updateProduct(@PathVariable Integer id, @RequestBody UpdateAnnouncementDto ann) {
        return service.updateAnn( id, ann);
    }

    @GetMapping("/pages")
    public PageDTO<AllAnnouncementDto> getAllPageAnn(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "0") int category,
        @RequestParam(defaultValue = "active") String mode){
        return service.getAllPageAnn(page, size, mode ,category);
    }
}
