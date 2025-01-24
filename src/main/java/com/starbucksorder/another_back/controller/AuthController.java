package com.starbucksorder.another_back.controller;

import com.starbucksorder.another_back.aspect.annotation.ValidAop;
import com.starbucksorder.another_back.dto.admin.request.ReqSigninDto;
import com.starbucksorder.another_back.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @ValidAop
    @Operation(summary = "로그인", description = "로그인을 위한 메소드")
    @PostMapping("/auth/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody ReqSigninDto dto, BindingResult bindingResult) {
        System.out.println("실행됨");
        return ResponseEntity.ok().body(authService.signin(dto));
    }

    @Operation(summary = "토큰", description = "토큰 확인 메서드")
    @GetMapping("/admin/auth/access")
    public ResponseEntity<?> access(String accessToken) {
        return ResponseEntity.ok().body(authService.isValidAccessToken(accessToken));
    }
}
