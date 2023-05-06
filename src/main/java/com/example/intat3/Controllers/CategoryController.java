package com.example.intat3.Controllers;

import com.example.intat3.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.intat3.Entity.Category;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://intproj22.sit.kmutt.ac.th:8080/at3")
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping
    public List<Category> getAllCategory (){
        return  service.getAllCategory();
    }
    
    
    
    
}
