package com.study.realworld.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.security.core.Authentication;

public class JwtTokenProvider {

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

    // TODO : payload sub값 차후 pk로 변경 필요 + 사용자 정보 claim 추가 필요
    public String generateToken(Authentication authentication) {
        long now = (new Date()).getTime();

        Date issuedAtDate = new Date();
        Date accessTokenExpiresIn = new Date(now + accessTime);
        String accessToken = Jwts.builder()
            .signWith(key, SignatureAlgorithm.HS512)            // header "alg" : HS512
            .setHeaderParam("typ", headerType)            // header "typ" : JWT
            .setSubject(authentication.getName())               // payload "sub" : username
            .setIssuer(issuer)                                  // payload "iss" : ori
            .setExpiration(accessTokenExpiresIn)                // payload "exp"
            .setIssuedAt(issuedAtDate)                          // payload "iat"
            .compact();

        return accessToken;
    }

}
