package com.study.realworld.global.security;

import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.global.exception.JwtException;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
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
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JjwtService implements JwtService {

    private static final String JWT_HEADER_PARAM_TYPE = "typ";

    private final Key key;

    private final String headerType;

    private final String issuer;

    private final long accessTime;

    private final UserRepository userRepository;

    public JjwtService(@Value("${jwt.token.header-type}") String headerType,
        @Value("${jwt.token.issuer}") String issuer,
        @Value("${jwt.token.secret}") String secret,
        @Value("${jwt.token.access-time}") long accessTime,
        UserRepository userRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.headerType = headerType;
        this.issuer = issuer;
        this.accessTime = accessTime;
        this.userRepository = userRepository;
    }

    public String createToken(User user) {
        return Jwts.builder()
            .signWith(key, SignatureAlgorithm.HS512)
            .setHeaderParam(JWT_HEADER_PARAM_TYPE, headerType)
            .setSubject(user.id().toString())
            .setIssuer(issuer)
            .setExpiration(new Date((new Date()).getTime() + accessTime))
            .setIssuedAt(new Date())
            .compact();
    }

    public Optional<User> getUser(String accessToken) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(accessToken).getBody();
            Long userId = Long.parseLong(claims.getSubject());
            return userRepository.findById(userId);
        } catch (ExpiredJwtException e) {
            throw new JwtException(ErrorCode.INVALID_EXPIRED_JWT);
        } catch (SecurityException | MalformedJwtException e) {
            throw new JwtException(ErrorCode.INVALID_MALFORMED_JWT);
        } catch (UnsupportedJwtException e) {
            throw new JwtException(ErrorCode.INVALID_UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            throw new JwtException(ErrorCode.INVALID_ILLEGAL_ARGUMENT_JWT);
        }
    }

}
