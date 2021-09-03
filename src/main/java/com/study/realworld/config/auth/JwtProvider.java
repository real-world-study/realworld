package com.study.realworld.config.auth;

import com.study.realworld.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    private String secret;
    private int accessTime;

    public JwtProvider(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.access-time}") int accessTime) {
        this.secret = secret;
        this.accessTime = accessTime;
    }

    public String generateJwtToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setHeader(createHeader())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTime))
                .signWith(createKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        try {
             return Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | NullPointerException e) {
            throw new JwtException("Invalid token");
        }
    }

    public String getSubjectFromToken(String token) {
        return this.getClaimsFromToken(token)
                .getSubject();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS512");
        header.put("typ", "JWT");
        return header;
    }

    private Key createKey() {
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secret);
        return new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

}
