package com.study.realworld.core.jwt;

import com.study.realworld.core.domain.user.entity.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * @author JeongJoon Seo
 */

@Component
public class TokenProvider {

    private final long tokenValidityInMilliseconds;

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        final byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(User user) {
        long nowTime = (new Date()).getTime();
        return Jwts.builder()
                   .signWith(key, SignatureAlgorithm.HS512)
                   .setSubject(user.getId().toString())
                   .setExpiration(new Date(nowTime + tokenValidityInMilliseconds))
                   .compact();
    }

    public Long getUserId(String accessToken) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
