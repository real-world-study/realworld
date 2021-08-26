package com.study.realworld.domain.auth.infrastructure;

import com.study.realworld.domain.auth.dto.ResponseToken;
import com.study.realworld.domain.auth.dto.token.AccessToken;
import com.study.realworld.domain.auth.dto.token.RefreshToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.lang.Math.addExact;

@Component
public class TokenProvider {
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") final String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public ResponseToken createToken(final Authentication authentication) {
        final long now = (new Date()).getTime();
        final AccessToken accessToken = accessToken(authentication, now);
        final RefreshToken refreshToken = refreshResponseToken(now);
        return new ResponseToken(accessToken, refreshToken);
    }

    private AccessToken accessToken(final Authentication authentication, final long now) {
        return new AccessToken(Jwts.builder()
                .signWith(key, HS512)
                .setSubject(authentication.getName())
                .setExpiration(new Date(addExact(now, ACCESS_TOKEN_EXPIRE_TIME)))
                .compact());
    }

    private RefreshToken refreshResponseToken(final long now) {
        return new RefreshToken(Jwts.builder()
                .signWith(key, HS512)
                .setExpiration(new Date(addExact(now, REFRESH_TOKEN_EXPIRE_TIME)))
                .compact());
    }

    public boolean validateToken(final String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public final String mapToUsername(final String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(final String accessToken) {
        final JwtParser jwtParser = jwtParser();
        return jwtParser.parseClaimsJws(accessToken).getBody();
    }

    private JwtParser jwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

}
