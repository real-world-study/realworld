package com.study.realworld.domain.auth.infrastructure;

import com.study.realworld.domain.auth.dto.ResponseToken;
import com.study.realworld.domain.user.domain.*;
import com.study.realworld.global.jwt.error.exception.JwtParseException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.Date;

import static com.study.realworld.domain.user.domain.BioTest.BIO;
import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        final User user = UserTest.userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));

        final ResponseToken token = tokenProvider.createToken(user);
        assertAll(
                () -> assertThat(token).isNotNull(),
                () -> assertThat(token).isInstanceOf(ResponseToken.class)
        );
    }

    @DisplayName("TokenProvider 인스턴스 mapToUsername() 테스트")
    @Test
    void mapToUser_test() {
        final TokenProvider tokenProvider = new TokenProvider(TEST_KEY);
        assertThat(tokenProvider.mapToUsername(testToken())).isEqualTo(EMAIL);
    }

    @DisplayName("TokenProvider 인스턴스 mapToUsername() 잘못된 토큰값으로 인한 실패 테스트")
    @Test
    void mapToUser_fail_test() {
        final TokenProvider tokenProvider = new TokenProvider(TEST_KEY);
        assertThatThrownBy(() -> tokenProvider.mapToUsername("invalidToken"))
                .isInstanceOf(JwtParseException.class)
                .hasMessage("Jwt parsing failed.");
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