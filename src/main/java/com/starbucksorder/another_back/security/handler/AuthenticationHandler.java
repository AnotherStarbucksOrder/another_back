package com.starbucksorder.another_back.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // UsernamePasswordAuthenticationFilter 필터가 동작할 때 'Authentication' 객체가 없는 경우 동작
        // 토큰필터에서 'Authentication' 객체를 만들어 주지 못한 경우 동작
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(401);
            response.getWriter().println("토큰이 유효하지 않습니다!");

    }
}
