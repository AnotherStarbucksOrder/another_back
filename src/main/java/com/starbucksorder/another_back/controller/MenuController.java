package com.starbucksorder.another_back.controller;

import com.starbucksorder.another_back.aspect.annotation.Log;
import com.starbucksorder.another_back.dto.admin.request.menu.ReqAdminDto;
import com.starbucksorder.another_back.dto.admin.request.menu.ReqAdminMenuListDtoAll;
import com.starbucksorder.another_back.dto.admin.request.menu.ReqAdminMenuDto;
import com.starbucksorder.another_back.dto.admin.request.menu.ReqAdminModifyDto;
import com.starbucksorder.another_back.dto.user.request.menu.ReqMenuListDto;
import com.starbucksorder.another_back.service.DuplicateService;
import com.starbucksorder.another_back.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private DuplicateService duplicateService;

    // 카테고리별 메뉴리스트 종류 -> 12개씩
    @GetMapping("/home/category/menus")
    public ResponseEntity<?> getMenuList(@RequestParam ReqMenuListDto dto) {
        return ResponseEntity.ok().body(menuService.getMenuList(dto));
    }

    // 메뉴id별 메뉴정보
    @GetMapping("/menu/{menuId}")
    public ResponseEntity<?> getMenuById(@PathVariable Long menuId) {
        return ResponseEntity.ok().body(menuService.getMenu(menuId));
    }

    /* NOTE: --------------관리자 메뉴추가를 위한 로직-------------- */

    // 메뉴추가를 하기위한 옵션과 카테고리 불러오기
    @GetMapping("/admin/menu/values")
    public ResponseEntity<?> getNames() {
        return ResponseEntity.ok().body(menuService.getValueAll());
    }

    // 카테고리 또는 옵션에 들어갈 메뉴 전체조회
    @GetMapping("/admin/menu")
    public ResponseEntity<?> getMenuListAll() {
        return ResponseEntity.ok().body(menuService.getMenuListAll());
    }

    // 메뉴관리 조회 -> 다 건 조회
    // 검색기능 추가
    // FIXME: 단비누나 이거 메소드명 잘봐주세요
    @GetMapping("/admin/menus")
    public ResponseEntity<?> getAllMenus(ReqAdminMenuDto dto) {
        System.out.println("동작됨");
        return ResponseEntity.ok().body(menuService.getAllMenus(dto));
    }

    // 메뉴 추가
    @PostMapping("/admin/menu")
    public ResponseEntity<?> addMenu(@RequestBody ReqAdminDto dto) {
        return ResponseEntity.ok().body(menuService.addMenu(dto));
    }

    // 메뉴 상세보기
    @GetMapping("/admin/menu/detail/{menuId}")
    public ResponseEntity<?> getMenuDetail(@PathVariable Long menuId) {
        return ResponseEntity.ok().body(menuService.getMenuDetail(menuId));
    }

    // 메뉴 삭제
    @DeleteMapping("/admin/menu/{menuId}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long menuId) {
        return ResponseEntity.ok().body(menuService.deleteMenu(menuId));
    }

    // 메뉴수정
    @Log
    @PatchMapping("/admin/modify/{menuId}")
    public ResponseEntity<?> modifyMenu(@PathVariable Long menuId, @RequestBody ReqAdminModifyDto dto) {
        return ResponseEntity.ok().body(menuService.modifyMenu(dto));
    }

    // 메뉴 상태변경
    @PatchMapping("/admin/menu/status/{menuId}")
    public ResponseEntity<?> updateMenuStatus(@PathVariable Long menuId) {
        return ResponseEntity.ok().body(menuService.updateMenuStatus(menuId));
    }

    /* NOTE: ------------------------------------------------------------ */

    // 자소분리현상 로직
    @PatchMapping("/admin/modify")
    public ResponseEntity<?> modifyMenu(@RequestBody List<ReqAdminMenuListDtoAll> menuList) {
        menuService.modifyMenu(menuList);
        return ResponseEntity.ok().body(null);
    }

}
