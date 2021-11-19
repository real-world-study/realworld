package com.study.realworld.global.common;

import com.study.realworld.global.common.TokenProviderDto;
import com.study.realworld.global.common.AccessToken;
import com.study.realworld.domain.user.domain.vo.UserEmail;
import com.study.realworld.global.jwt.JwtAuthentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component
public class TokenProvider {
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") final String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public AccessToken createToken(final TokenProviderDto tokenProviderDto) {
        return new AccessToken(Jwts.builder()
                .setSubject(tokenProviderDto.userEmail().value())
                .setExpiration(expiration())
                .signWith(key, HS512)
                .compact());
    }

    private Date expiration() {
        final LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(ACCESS_TOKEN_EXPIRE_TIME);
        return java.util.Date.from(localDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public boolean validate(final String token) {
        return subject(token).isPresent();
    }

    private Optional<String> subject(final String token) {
        try {
            return Optional.of(Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Authentication getAuthentication(final String jwt) {
        final String email = subject(jwt).orElseThrow(IllegalArgumentException::new);
        final UserEmail userEmail = UserEmail.from(email);
        return JwtAuthentication.from(userEmail);
    }
}
