package com.starbucksorder.another_back.controller;

import com.starbucksorder.another_back.aspect.LogAspect;
import com.starbucksorder.another_back.aspect.annotation.Log;
import com.starbucksorder.another_back.aspect.annotation.ValidAop;
import com.starbucksorder.another_back.dto.admin.ReqAdminPageAndLimitDto;
import com.starbucksorder.another_back.dto.admin.request.category.ReqAdminCategoryDto;
import com.starbucksorder.another_back.dto.admin.request.category.ReqAdminIncludeMenuByCategoryDto;
import com.starbucksorder.another_back.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LogAspect logAspect;

    // 전체 카테고리 조회
    @Operation(summary = "전체카테고리", description = "전체카테고리 ")
    @GetMapping("/category")
    public ResponseEntity<?> getCategory() {
        return ResponseEntity.ok().body(categoryService.getCategory());
    }


    /* NOTE: ---------------------------------ADMIN 관련----------------------------------------*/

    @Log
    @ValidAop
    @Operation(summary = "카테고리 등록")
    @PostMapping("/admin/category")
    public ResponseEntity<?> add(@RequestBody @Valid ReqAdminCategoryDto dto, BindingResult bindingResult) {
        return ResponseEntity.ok().body(categoryService.add(dto));
    }

    @Operation(summary = "메뉴에 카테고리 부여하기")
    @PostMapping("/admin/category/menu")
    public ResponseEntity<?> includeMenu(@RequestBody ReqAdminIncludeMenuByCategoryDto dto) {
        return ResponseEntity.ok().body(categoryService.includeMenusByCategoryId(dto));
    }

    @Operation(summary = "카테고리 전체 조회")
    @GetMapping("/admin/category")
    public ResponseEntity<?> getAllCategories(ReqAdminPageAndLimitDto dto) {
        return ResponseEntity.ok().body(categoryService.getAllCategories(dto));
    }

    @Operation(summary = "카테고리 상세보기 및 해당 메뉴 불러오기", description = "카테고리 id로 상세보기 및 해당 메뉴 불러오기")
    @GetMapping("/admin/category/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(categoryService.getCategoryById(categoryId));
    }

    @Operation(summary = "카테고리 id로 연쇄삭제", description = "카테고리 id로 삭제(프로시저로 인한 연쇄삭제)하기")
    @DeleteMapping("/admin/category/{categoryId}")
    public ResponseEntity<?> delete(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(categoryService.delete(categoryId));
    }

    @ValidAop
    @Operation(summary = "카테고리 수정", description = "카테고리 id로 카테고리 수정하기")
    @PatchMapping("/admin/category/{categoryId}")
    public ResponseEntity<?> update(@PathVariable Long categoryId, @Valid @RequestBody ReqAdminCategoryDto dto, BindingResult bindingResult) {
        return ResponseEntity.ok().body(categoryService.update(dto));
    }

    @Operation(summary = "카테고리 활성/비활성화", description = "카테고리 id로 활성/비활성화 수정하기")
    @PatchMapping("/admin/category/status/{categoryId}")
    public ResponseEntity<?> updateStatus(@PathVariable Long categoryId) {
        categoryService.updateStatus(categoryId);
        return ResponseEntity.ok().body(true);
    }
}