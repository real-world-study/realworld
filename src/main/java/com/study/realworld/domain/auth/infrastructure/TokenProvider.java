package com.study.realworld.domain.auth.infrastructure;

import com.study.realworld.domain.auth.dto.ResponseToken;
import com.study.realworld.domain.auth.dto.token.AccessToken;
import com.study.realworld.domain.auth.dto.token.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.util.Objects.isNull;

@Component
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String DELIMITER = ",";
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

    private RefreshToken refreshResponseToken(final long now) {
        return new RefreshToken(Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, HS512)
                .compact());
    }

    private AccessToken accessToken(final Authentication authentication, final long now) {
        return new AccessToken(Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities(authentication))
                .setExpiration(accessTokenExpiresIn(now))
                .signWith(key, HS512)
                .compact());
    }

    private String authorities(final Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(DELIMITER));
    }

    private Date accessTokenExpiresIn(final long now) {
        return new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
    }

    public final String determineUsername(final String token) {
        final Claims claims = parseClaims(token);
        if (isNull(claims.get(AUTHORITIES_KEY))) {
            throw new IllegalArgumentException();
        }
        return claims.getSubject();
    }

    private Claims parseClaims(final String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean validateToken(final String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
