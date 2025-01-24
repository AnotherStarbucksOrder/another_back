package com.starbucksorder.another_back.controller;

import com.starbucksorder.another_back.aspect.annotation.ValidAop;
import com.starbucksorder.another_back.dto.admin.ReqAdminDeleteDto;
import com.starbucksorder.another_back.dto.admin.request.ReqAdminUserDto;
import com.starbucksorder.another_back.dto.admin.request.user.ReqAdminSearchDto;
import com.starbucksorder.another_back.service.PointService;
import com.starbucksorder.another_back.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
// HACK: 매핑명 변경 /points -> point
public class UserController {

    @Autowired
    private PointService pointService;

    @Autowired
    private UserService userService;

    @Operation(summary = "포인트 사용을 위한 전화번호로 조회요청")
    @GetMapping("/point/user/reward")
    public ResponseEntity<?> getRewardPoint(@RequestParam String phoneNumber) {
        return ResponseEntity.ok().body(pointService.getUserIdByPhoneNumber(phoneNumber));
    }

    // NOTE: 관리자 회원관리

    @ValidAop
    @Operation(summary = "사용자 등록")
    @PostMapping("/admin/user")
    public ResponseEntity<?> addUser(@RequestBody @Valid ReqAdminUserDto dto, BindingResult bindingResult) {
        return ResponseEntity.ok().body(userService.addUser(dto));
    }

    // 페이지번호, 검색어
    @Operation(summary = "사용자 전화번호, 메모에 대한 전체 조회 페이징")
    @GetMapping("/admin/user")
    public ResponseEntity<?> getUserAll(ReqAdminSearchDto dto) {
        return ResponseEntity.ok().body(userService.getUserAll(dto));
    }

    @Operation(summary = "회원 정보 상세보기", description = "사용자 id를 통해 회원 정보 상세보기")
    @GetMapping("/admin/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @Operation(summary = "회원 삭제", description = "사용자id를 통해 회원 삭제하기")
    @DeleteMapping("/admin/user")
    public ResponseEntity<?> deleteUser(ReqAdminDeleteDto dto) {
        // {params: userIds:[1,2,3]}
        return ResponseEntity.ok().body(userService.deleteUserByIds(dto));
    }

    @ValidAop
    @Operation(summary = "회원 수정", description = "사용자id를 통해 회원 수정하기")
    @PatchMapping("/admin/user/modify/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody @Valid ReqAdminUserDto dto, BindingResult bindingResult) {
        return ResponseEntity.ok().body(userService.updateUser(dto));
    }
}