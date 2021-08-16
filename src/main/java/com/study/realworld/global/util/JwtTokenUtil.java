package com.study.realworld.global.util;

import com.study.realworld.user.dto.UserLoginRequest;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.User;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class JwtTokenUtil {

    private static final String SECRET_KEY = "real-world-with-hoony-lab";

    public static String generateJwtToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setHeader(createHeader())
                .setClaims(createClaims(user.getUsername()))
                .setExpiration(createExpirationForOneMinute())
                .signWith(SignatureAlgorithm.HS512, createKey())
                .compact();
    }

    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    public static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return true;
        } catch (MalformedJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (PrematureJwtException e) {
            return false;
        } catch (ClaimJwtException e) {
            return false;
        } catch (JwtException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "RS512");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private static Map<String, Object> createClaims(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("role", "USER_SOMETHING");
        return claims;
    }

    private static Date createExpirationForOneMinute() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 1);
        return c.getTime();
    }

    private static Key createKey() {
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        return new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

//    private static Role getRoleFromToken(String token) {
//        Claims claims = getClaimsFromToken(token);
//        return (Role) claims.get("role");
//    }

}
