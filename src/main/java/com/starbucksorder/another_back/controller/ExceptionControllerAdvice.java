package com.starbucksorder.another_back.controller;

import com.starbucksorder.another_back.exception.BadCredentialException;
import com.starbucksorder.another_back.exception.DuplicateNameException;
import com.starbucksorder.another_back.exception.UserNotFoundException;
import com.starbucksorder.another_back.exception.ValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    // UserNotFoundException 예외처리
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> notFoundBoardException(UserNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    // 해당이름 없음 예외처리 주로 조회 때 사용
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> notFoundUsernameException(UsernameNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    // 관리자 비밀번호 에러 예외처리 ( e.getMessage에 명확하게 띄워줘선 안되지만 테스트용도이기에 활성화)
    @ExceptionHandler(BadCredentialException.class)
    public ResponseEntity<?> badCredentialException(BadCredentialException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }

    // 유효성검사 관리자 로그인시 사용
    @ExceptionHandler(ValidException.class)
    public ResponseEntity<?> validException(ValidException e) {
        return ResponseEntity.status(401).body(e.getFieldErrors());
    }

    // 중복된 이름 예외처리 메뉴, 옵션, 카테고리 등록시에 사용
    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity<?> duplicateNameException(DuplicateNameException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
