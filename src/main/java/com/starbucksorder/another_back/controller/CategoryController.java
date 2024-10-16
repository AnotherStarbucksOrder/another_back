package com.starbucksorder.another_back.controller;

import com.starbucksorder.another_back.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 전체 카테고리 조회
    @GetMapping("/category")
    public ResponseEntity<?> getCategory() {
        return ResponseEntity.ok().body(categoryService.getCategory());
    }
}
