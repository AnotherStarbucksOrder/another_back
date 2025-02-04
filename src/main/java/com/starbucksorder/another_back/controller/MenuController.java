package com.starbucksorder.another_back.controller;

import com.starbucksorder.another_back.aspect.annotation.ValidAop;
import com.starbucksorder.another_back.dto.admin.ReqAdminDeleteDto;
import com.starbucksorder.another_back.dto.admin.request.menu.ReqAdminDto;
import com.starbucksorder.another_back.dto.admin.request.menu.ReqAdminMenuDto;
import com.starbucksorder.another_back.dto.admin.request.menu.ReqAdminModifyDto;
import com.starbucksorder.another_back.dto.user.request.Order.ReqOrderItem;
import com.starbucksorder.another_back.dto.user.request.menu.ReqMenuListDto;
import com.starbucksorder.another_back.service.DuplicateService;
import com.starbucksorder.another_back.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MenuController {

    private final MenuService menuService;
    private final DuplicateService duplicateService;

    @Operation(summary = "사용자 메뉴리스트 불러오기", description = " 12개씩 메뉴리스트 불러오기")
    @GetMapping("/home/category/menus")
    public ResponseEntity<?> getMenuList(ReqMenuListDto dto) {
        return ResponseEntity.ok().body(menuService.getMenuList(dto));
    }

    @Operation(summary = "사용자 메뉴선택시 상세보기", description = "menuId로 해당 메뉴 상세보기")
    @GetMapping("/menu/{menuId}")
    public ResponseEntity<?> getMenuById(@PathVariable Long menuId) {
        return ResponseEntity.ok().body(menuService.getMenu(menuId));
    }

    /* NOTE: --------------관리자 메뉴추가를 위한 로직-------------- */

    @Operation(summary = "메뉴추가", description = " 메뉴 추가 하기위한 전체 옵션, 카테고리 불러오기")
    @GetMapping("/admin/menu/values")
    public ResponseEntity<?> getNames() {
        return ResponseEntity.ok().body(menuService.getValueAll());
    }

    @Operation(summary = "메뉴 전체조회", description = "카테고리관리, 옵션관리에서 메뉴들을 다중선택할 때 사용")
    @GetMapping("/admin/menu")
    public ResponseEntity<?> getMenuListAll() {
        return ResponseEntity.ok().body(menuService.getMenuListAll());
    }

    @Operation(summary = "메뉴이름 또는 카테고리에 대한 전체조회", description = "페이징처리 및 해당하는 전체 갯수보여주기")
    @GetMapping("/admin/menus")
    public ResponseEntity<?> getAllMenus(ReqAdminMenuDto dto) {
        return ResponseEntity.ok().body(menuService.getAllMenus(dto));
    }

    @ValidAop
    @Operation(summary = "메뉴 추가")
    @PostMapping("/admin/menu")
    public ResponseEntity<?> addMenu(@RequestBody @Valid ReqAdminDto dto, BindingResult bindingResult) {
        return ResponseEntity.ok().body(menuService.addMenu(dto));
    }

    @Operation(summary = "메뉴 상세보기", description = "메뉴 id에 대한 상세보기(옵션, 카테고리)")
    @GetMapping("/admin/menu/detail/{menuId}")
    public ResponseEntity<?> getMenuDetail(@PathVariable Long menuId) {
        return ResponseEntity.ok().body(menuService.getMenuDetail(menuId));
    }

    @Operation(summary = "메뉴 삭제", description = "메뉴 id 리스트로 받아서 한번에 삭제하기")
    @DeleteMapping("/admin/menu")
    public ResponseEntity<?> deleteMenu(ReqAdminDeleteDto dto) {
        return ResponseEntity.ok().body(menuService.deleteMenu(dto));
    }

    @ValidAop
    @Operation(summary = "메뉴 수정", description = "해당 메뉴 id로 메뉴 수정하기")
    @PatchMapping("/admin/modify/{menuId}")
    public ResponseEntity<?> modifyMenu(@PathVariable Long menuId, @RequestBody @Valid ReqAdminModifyDto dto, BindingResult bindingResult) {
        return ResponseEntity.ok().body(menuService.modifyMenu(dto));
    }

    @Operation(summary = "메뉴 활성/비활성", description = "활성/비활성 상태 수정")
    @PatchMapping("/admin/menu/status/{menuId}")
    public ResponseEntity<?> updateMenuStatus(@PathVariable Long menuId) {
        return ResponseEntity.ok().body(menuService.updateMenuStatus(menuId));
    }

    @Operation(summary = "쿠폰 조회 메뉴 조회", description = "쿠폰 결제 시 적용될 메뉴들 조회하기")
    @GetMapping("/product/items")
    public ResponseEntity<?> getProductItem(ReqOrderItem dto) {
        return ResponseEntity.ok().body(menuService.findByIds(dto));
    }

}
