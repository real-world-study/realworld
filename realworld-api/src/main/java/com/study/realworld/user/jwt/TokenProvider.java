package com.study.realworld.user.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import com.study.realworld.exception.CustomException;
import com.study.realworld.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Token";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto createToken(Authentication authentication) {
        final String authorities = authentication.getAuthorities().stream()
                                                 .map(GrantedAuthority::getAuthority)
                                                 .collect(Collectors.joining(","));

        final long now = (new Date()).getTime();

        final Date accessTokenExpireDate = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        final String accessToken = Jwts.builder()
                                       .setSubject(authentication.getName())
                                       .claim(AUTHORITIES_KEY, authorities)
                                       .setExpiration(accessTokenExpireDate)
                                       .signWith(key, SignatureAlgorithm.HS512)
                                       .compact();

        final String refreshToken = Jwts.builder()
                                        .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                                        .signWith(key, SignatureAlgorithm.HS512)
                                        .compact();

        return TokenDto.builder()
                       .grantType(BEARER_TYPE)
                       .accessToken(accessToken)
                       .accessTokenExpireDate(accessTokenExpireDate.getTime())
                       .refreshToken(refreshToken)
                       .build();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                      .map(SimpleGrantedAuthority::new)
                      .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
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
            return Jwts.parserBuilder()
                       .setSigningKey(key)
                       .build()
                       .parseClaimsJws(accessToken)
                       .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
