package com.starbucksorder.another_back.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

// 커스텀 핸들러
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 문자열 타입 또는 html 타입 등
        response.setContentType("/text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(403);
        response.getWriter().println("접근 권한이 없습니다.");
    }
}
