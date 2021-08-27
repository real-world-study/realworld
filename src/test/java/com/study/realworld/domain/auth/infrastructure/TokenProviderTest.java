package com.study.realworld.domain.auth.infrastructure;

import com.study.realworld.domain.auth.dto.ResponseToken;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.global.config.security.jwt.JwtAuthentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TokenProviderTest {

    // spring-boot-security-jwt-tutorial-jiwoon-spring-boot-security-jwt-tutorial
    public static final String TEST_KEY = "c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LXR1dG9yaWFsLWppd29vbi1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHV0b3JpYWwK";

    @DisplayName("TokenProvider 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final TokenProvider tokenProvider = new TokenProvider(TEST_KEY);
        assertAll(
                () -> assertThat(tokenProvider).isNotNull(),
                () -> assertThat(tokenProvider).isExactlyInstanceOf(TokenProvider.class)
        );
    }

    @DisplayName("TokenProvider 인스턴스 createToken() 테스트")
    @Test
    void createToken_test() {
        final TokenProvider tokenProvider = new TokenProvider(TEST_KEY);
        final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(User.DEFAULT_AUTHORITY);
        final JwtAuthentication authentication = new JwtAuthentication(EMAIL, PASSWORD, Collections.singleton(authority));

        final ResponseToken token = tokenProvider.createToken(authentication);
        assertAll(
                () -> assertThat(token).isNotNull(),
                () -> assertThat(token).isInstanceOf(ResponseToken.class)
        );
    }

    @DisplayName("TokenProvider 인스턴스 validateToken() 테스트")
    @Test
    void validateToken_test() {
        final TokenProvider tokenProvider = new TokenProvider(TEST_KEY);

        assertAll(
                () -> assertThat(tokenProvider.validateToken(testToken())).isTrue(),
                () -> assertThat(tokenProvider.validateToken("failToken")).isFalse()
        );
    }

    @DisplayName("TokenProvider 인스턴스 mapToUsername() 테스트")
    @Test
    void mapToUser_test() {
        final TokenProvider tokenProvider = new TokenProvider(TEST_KEY);
        assertThat(tokenProvider.mapToUsername(testToken())).isEqualTo(EMAIL);
    }

    public static final String testToken() {
        final byte[] keyBytes = Decoders.BASE64.decode(TEST_KEY);
        final Key key = Keys.hmacShaKeyFor(keyBytes);
        final long now = (new Date()).getTime();
        return Jwts.builder()
                .setSubject(EMAIL)
                .setExpiration(new Date(now + 300_000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

}