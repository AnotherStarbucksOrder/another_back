package com.starbucksorder.another_back.security.filter;

import com.starbucksorder.another_back.domain.Admin;
import com.starbucksorder.another_back.repository.AdminMapper;
import com.starbucksorder.another_back.security.jwt.JwtProvider;
import com.starbucksorder.another_back.security.principal.PrincipalUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AdminMapper adminMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 우리 토큰이 제대로 되있는건지 확인만 해주면 됨
        String requestURI = request.getRequestURI().toString();
        List<String> permitAllUrls = List.of(
                "/menu", "/user", "/category", "/home", "/error",
                "/swagger-ui", "/v3/api-docs", "/swagger-resources", "/webjars"
        );

        if (permitAllUrls.contains(requestURI.substring(0, requestURI.indexOf("/", 1) != -1 ? requestURI.indexOf("/", 1) : requestURI.length()))) {
            filterChain.doFilter(request, response);
            return;
        }

        String bearerToken = request.getHeader("Authorization");
        // 토큰 검사
        if(bearerToken == null || bearerToken.isBlank()) {
            filterChain.doFilter(request, response); // 다음 필터로 넘기는거
            return;
        }

        // 토큰 인증
        String token = jwtProvider.removeBearerToken(bearerToken); // 토큰 형태 만들어주는 것
        Claims claims = null;
        try {
            claims = jwtProvider.parseToken(token);
            Long adminId = ((Integer)claims.get("adminId")).longValue();
            Admin admin = adminMapper.findById(adminId);
            if(admin == null) {
                throw new JwtException("해당 유저를 찾을 수 없습니다.");
            }
            PrincipalUser principalUser = admin.toPrincipal();

            // 요청이 들어오면, 스프링시큐리티가 시큐리티컨텍스트홀더, 컨텍스트, authentication 자동으로 만들어져
            // 우리가 principalUser를 authentication에 담아줘야하는게 문제인거지

            // 이게 우리가 검증한 인증 객체
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {
            e.printStackTrace();
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}




//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//
//        String requestURI = httpServletRequest.getRequestURI().toString();
//        List<String> permitAllUrls = List.of(
//                "/menu",
//                "/user",
//                "/category",
//                "/home",
//                "/error"
//        );
//        if (permitAllUrls.contains(requestURI.substring(0, requestURI.indexOf("/", 1) != -1 ? requestURI.indexOf("/", 1) : requestURI.length()))) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        String bearerToken = httpServletRequest.getHeader("Authorization");
//        // FIXME: !bearerToken.startsWith("Bearer ") -> isBlank로 변경 됨
//        if (bearerToken == null || bearerToken.isBlank()) {
//            chain.doFilter(request, response);
//            return;
//        }
//        String token = jwtProvider.removeBearerToken(bearerToken);
//        Claims payLoad = null;
//        try {
//            payLoad = jwtProvider.parseToken(token);
//            Long adminId = ((Integer) payLoad.get("adminId")).longValue();
//            Admin admin = adminMapper.findById(adminId);
//            if (admin == null) {
//                throw new JwtException("user Not Found");
//            }
//            PrincipalUser principalUser = admin.toPrincipal();
//            Authentication authentication = new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } catch (JwtException e) {
//            e.printStackTrace();
//            chain.doFilter(request, response);
//            return;
//        }
//        chain.doFilter(request, response);
//    }

