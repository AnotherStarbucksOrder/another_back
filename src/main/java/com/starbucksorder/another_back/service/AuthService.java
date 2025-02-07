package com.starbucksorder.another_back.service;

import com.starbucksorder.another_back.aspect.LogAspect;
import com.starbucksorder.another_back.dto.admin.request.ReqSigninDto;
import com.starbucksorder.another_back.domain.Admin;
import com.starbucksorder.another_back.exception.AccessTokenValidException;
import com.starbucksorder.another_back.exception.BadCredentialException;
import com.starbucksorder.another_back.repository.AdminMapper;
import com.starbucksorder.another_back.security.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private LogAspect logAspect;

    // 로그인
    public String signin(ReqSigninDto dto) {
        // 아이디확인 Optional
        Admin admin = adminMapper.findByUserName(dto.getUsername()).orElseThrow(
                // 없어도 되긴해서 얘만 생성하지 않아 봄 (Advice만 생성)
                () -> new UsernameNotFoundException("Username not found")
        );
        System.out.println(admin);
        // 비밀번호 확인
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), admin.getPassword())) {
            throw new BadCredentialException("Bad credentials");
        }

        // 토큰 생성
        String accessToken = jwtProvider.generateToken(admin);
        System.out.println(accessToken);
        return accessToken;
        // 로그인 자체에서 generatedTotken으로 구현
        // aspect bindingresult 구현 후
    }

    // 토큰 유효성 검사
    public Boolean isValidAccessToken(String bearerAccessToken) {
        Claims claims = null;
        try {
            String accessToken = jwtProvider.removeBearerToken(bearerAccessToken);
            claims = jwtProvider.parseToken(accessToken);
            Long adminId = ((Integer) claims.get("adminId")).longValue();
            Admin user = adminMapper.findById(adminId);

            if (user == null) {
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            throw new AccessTokenValidException("AccessToken 유효성 검사 실패");
        }
        return true;
    }
}
