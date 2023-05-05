package com.example.intat3.services;

import com.example.intat3.Entity.Announcement;
import com.example.intat3.Entity.Category;
import com.example.intat3.repositories.AnnouncementRepository;
import com.example.intat3.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

//    public List<Announcement> getAnnouncementByCategory(String name) {
//        Category c = categoryRepository.findBy(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
//                "Category id " + name + "Does Not Exist !!!"));
//        return c.getAnnouncementList();
//    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}