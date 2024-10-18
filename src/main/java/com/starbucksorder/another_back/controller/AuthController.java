package com.starbucksorder.another_back.controller;

import com.starbucksorder.another_back.aspect.annotation.ValidAop;
import com.starbucksorder.another_back.dto.admin.request.ReqSigninDto;
import com.starbucksorder.another_back.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AuthController {
    @Autowired
    private AuthService authService;

    // 로그인
    @ValidAop
    @PostMapping("/auth/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody ReqSigninDto dto, BindingResult bindingResult){
        System.out.println("실행됨");
        return ResponseEntity.ok().body(authService.signin(dto));
    }
    // 토큰 확인
    @GetMapping("/auth/access")
    public ResponseEntity<?> access(String token){
        return ResponseEntity.ok().body(authService.isValidAccessToken(token));
    }
}
