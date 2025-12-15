package com.korit.post_mini_project_back.jwt;

import com.korit.post_mini_project_back.entity.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 만드는거 다른걸 별로 안중요하고 claim, signWith가 좀 중요
    public String createAccessToken(User user) {

        // 토큰 만료시간 계산
        Date now = new Date();
        Long expiredTime = now.getTime() + (1000L * 60L * 60L * 24L);
        Date expiredData = new Date(expiredTime);

        return Jwts.builder()
                .subject("server access token")
                .issuer("김준일")
                .issuedAt(new Date())
                .expiration(expiredData)
                // JWT 의 Payload는 JSON형태라서 적당히 원하는 데이터 넣으면됨 클라이언트는 이걸 decode해서 userId 확인하고, 백엔드는 토큰 검증 후 userId 꺼내서 사용
                .claim("userId", user.getUserId())
                // key → 서버가 갖고 있는 비밀키 HS256 → HMAC-SHA256 알고리즘
                .signWith(key, SignatureAlgorithm.HS256)
                // 이걸 하면 최종적으로 이런 형태의 문자열이 나옴: xxxxx.yyyyy.zzzzz (header).(payload).(signature)
                .compact();
    }

    // Token 유효성 검사 대충 JwtParser 임마가 알아서 해줌 ;; 실패하면 JwtException 터뜨려서 try catch 하는겨
    public boolean validateToken(String token) {
        try {
            JwtParser jwtParser = Jwts.parser()
                    .setSigningKey(key)
                    .build();
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // 토큰으로 userId 가져오기
    public int getUserId(String token) {
        return (int) Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .get("userId");
    }
}
