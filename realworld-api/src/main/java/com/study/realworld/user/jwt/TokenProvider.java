package com.study.realworld.user.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import com.study.realworld.exception.CustomException;
import com.study.realworld.exception.ErrorCode;
import com.study.realworld.user.entity.User.Authority;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                                           .map(GrantedAuthority::getAuthority)
                                           .collect(Collectors.joining(",")); // ROLE_USER

        if (!StringUtils.hasText(authorities)) {
            authorities = Authority.ROLE_USER.toString();
        }

        final long now = (new Date()).getTime();
        final Date accessTokenExpireDate = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        return Jwts.builder()
                   .setSubject(authentication.getName())
                   .claim(AUTHORITIES_KEY, authorities)
                   .setExpiration(accessTokenExpireDate)
                   .signWith(key, SignatureAlgorithm.HS512)
                   .compact();
    }

    public Authentication getAuthentication(String email, String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                      .map(SimpleGrantedAuthority::new)
                      .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(email, accessToken, authorities);
    }

    public boolean validateToken(String accessToken) {
        try {
            getClaims(accessToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Malformed JWT signingKey: {}", e);
        } catch (ExpiredJwtException e) {
            log.error("Jwt token is Expired: {}", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported Jwt: {}", e);
        } catch (IllegalArgumentException e) {
            log.error("Jwt is illegal: {}", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return getClaims(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getUserEmail(String accessToken) {
        try {
            final Claims claims = parseClaims(accessToken);
            return claims.getSubject();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }
    }

    private Jws<Claims> getClaims(final String accessToken) {
        return Jwts.parserBuilder()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(accessToken);
    }
}
