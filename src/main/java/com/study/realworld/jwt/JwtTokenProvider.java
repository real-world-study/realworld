package com.study.realworld.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtTokenProvider {

    private final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final Key key;

    private final String headerType;

    private final String issuer;

    private final long accessTime;

    public JwtTokenProvider(String headerType, String issuer, String secret, long accessTime) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.headerType = headerType;
        this.issuer = issuer;
        this.accessTime = accessTime;
    }

    public String generateToken(JwtAuthenticationTokenPrincipal authentication) {
        long now = (new Date()).getTime();

        Date issuedAtDate = new Date();
        Date accessTokenExpiresIn = new Date(now + accessTime);
        String accessToken = Jwts.builder()
            .signWith(key, SignatureAlgorithm.HS512)            // header "alg" : HS512
            .setHeaderParam("typ", headerType)            // header "typ" : JWT
            .setSubject(authentication.getId().toString())      // payload "sub" : userId
            .setIssuer(issuer)                                  // payload "iss" : ori
            .setExpiration(accessTokenExpiresIn)                // payload "exp"
            .setIssuedAt(issuedAtDate)                          // payload "iat"
            .compact();

        return accessToken;
    }

    public JwtAuthenticationToken getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        Long userId = Long.parseLong(claims.getSubject());

        return new JwtAuthenticationToken(new JwtAuthenticationTokenPrincipal(userId), null);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken)
                .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
    }

}
