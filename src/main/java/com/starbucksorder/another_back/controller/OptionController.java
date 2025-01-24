package com.starbucksorder.another_back.controller;

import com.starbucksorder.another_back.aspect.annotation.ValidAop;
import com.starbucksorder.another_back.dto.admin.ReqAdminPageAndLimitDto;
import com.starbucksorder.another_back.dto.admin.request.option.ReqAdminOptionDto;
import com.starbucksorder.another_back.service.MenuService;
import com.starbucksorder.another_back.service.OptionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OptionController {
    @Autowired
    private OptionService optionService;
    @Autowired
    private MenuService menuService;

    @ValidAop
    @Operation(summary = "옵션 추가 요청")
    @PostMapping("/admin/option")
    public ResponseEntity<?> addOption(@RequestBody @Valid ReqAdminOptionDto dto, BindingResult bindingResult) {
        return ResponseEntity.ok().body(optionService.addOption(dto));
    }

    // FIXME: 페이징처리 안함 -> 검토 필요
    @Operation(summary = "옵션 전체조회")
    @GetMapping("/admin/option")
    public ResponseEntity<?> getAllOptions(ReqAdminPageAndLimitDto dto) {
        return ResponseEntity.ok().body(optionService.getAllOptions(dto));
    }

    @Operation(summary = "옵션 상세보기 조회", description = "해당 옵션 id에 해당하는 상세정보 조회하기")
    @GetMapping("/admin/option/{optionId}")
    public ResponseEntity<?> getById(@PathVariable Long optionId) {
        return ResponseEntity.ok().body(optionService.getById(optionId));
    }

    @Operation(summary = "옵션에 해당하는 메뉴들 조회요청", description = "옵션 id들에 해당하는 메뉴들 불러오기")
    @GetMapping("/admin/option/menus")
    public ResponseEntity<?> getById(@RequestParam List<Long> ids) {
        return ResponseEntity.ok().body(optionService.getAllByOptionIds(ids));
    }

    @Operation(summary = "옵션 삭제 (연쇄삭제)", description = "옵션id에 해당하는 옵션 삭제하기(프로시저호출 연쇄삭제)")
    @DeleteMapping("/admin/option/{optionId}")
    public ResponseEntity<?> delete(@PathVariable Long optionId) {
        return ResponseEntity.ok().body(optionService.delete(optionId));
    }

    @ValidAop
    @Operation(summary = "옵션 수정하기", description = "옵션id에 해당하는 옵션 수정하기")
    @PatchMapping("/admin/option/{optionId}")
    public ResponseEntity<?> update(@PathVariable Long optionId, @RequestBody @Valid ReqAdminOptionDto dto, BindingResult bindingResult) {
        return ResponseEntity.ok().body(optionService.update(dto));
    }

    @Operation(summary = "옵션 활성/비활성화 상태 수정", description = "옵션id에 대한 활성/비활성 상태 수정하기")
    @PatchMapping("/admin/option/status/{optionId}")
    public ResponseEntity<?> updateStatus(@PathVariable Long optionId) {
        return ResponseEntity.ok().body(optionService.updateStatus(optionId));
    }
}
