package com.study.realworld.config.auth;

import com.study.realworld.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    private static final String SECRET_KEY = "real-world-with-hoony-lab";
    private static final int ACCESS_TIME = 30;

    public String generateJwtToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setHeader(createHeader())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * ACCESS_TIME))
                .signWith(SignatureAlgorithm.HS512, createKey())
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();
    }

    public String getSubjectFromToken(String token) {
        return this.getClaimsFromToken(token)
                .getSubject();
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = this.getClaimsFromToken(token);
            return true;
        } catch (JwtException | NullPointerException e) {
            return false;
        }
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS512");
        header.put("typ", "JWT");
        return header;
    }

    private Key createKey() {
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        return new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

}
