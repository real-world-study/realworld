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

@Component
public class JwtProvider {

    private final Key key;

    private final long accessTime = 1800000;    // 30분

    public JwtProvider(@Value("${jwt.token") String secret) {
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

    public Long getUserId(String accessToken) throws Exception {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            throw new Exception();
        }
    }

}
