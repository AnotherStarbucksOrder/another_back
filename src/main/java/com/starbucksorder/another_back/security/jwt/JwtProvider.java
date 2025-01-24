package com.starbucksorder.another_back.security.jwt;

import com.starbucksorder.another_back.entity.Admin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    private final Key key;

    // 디코딩
    public JwtProvider(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    // 토큰 만료일자
    private Date getExpirationDate() {
        return new Date(new Date().getTime() + 1000l * 60 * 60 * 24);
    }

    // 토큰 생성
    public String generateToken(Admin admin) {
        System.out.println("amdinId :" + admin.getAdminId());
        return Jwts.builder()
                .claim("adminId", admin.getAdminId()) // 토큰안에 데이터 담는 부분, 중간 부분
                .signWith(key, SignatureAlgorithm.HS256) // 시그니쳐알고리즘이 첫번째 머리 부분
                .expiration(getExpirationDate()) // 만료 일자
                .compact(); // build 이걸로 생성하겠다
    }

    // Bearer 삭제
    public String removeBearerToken(String token) {
        if (token == null && !token.startsWith("Bearer ")) {
            throw new RuntimeException();
        }
        return token.substring("Bearer ".length());
    }

    // 토큰 인증
    public Claims parseToken(String token) {
        JwtParser parser = Jwts.parser().setSigningKey(key).build(); // jwt를 검증할 수 있는 객체(키를 기반으로 하나 생성)
        return parser.parseClaimsJws(token).getPayload(); // 값이 틀리면 여기서 에러 발생
    }



}
