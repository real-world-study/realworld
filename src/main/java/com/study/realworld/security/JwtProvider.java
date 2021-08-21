package com.study.realworld.security;

import com.study.realworld.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * jwt를 생성하거나 jwt를 복호화하는 provider이다.
 * User를 받아 user내부에 있는 식별자 값을 subject에 저장하여 jwt를 생성한다.
 * accessToken을 받아 accessToken내부에 이미 저장한 subject를 가져와서 이를 반환한다.
 */
@Component
public class JwtProvider {

    private final Key key;

    private final long accessTime = 1800000;    // 30분

    public JwtProvider(@Value("${jwt.token}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(User user) {
        long nowTime = (new Date()).getTime();
        return Jwts.builder()
            .signWith(key, SignatureAlgorithm.HS512)
            .setSubject(user.getId().toString())
            .setExpiration(new Date(nowTime + accessTime))
            .compact();
    }

    public Long getUserId(String accessToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
