package com.study.realworld.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.study.realworld.user.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @Mock
    private User user;

    private JwtTokenProvider jwtTokenProvider;

    private String headerType = "headerType";
    private String issuser = "issuer";
    private String secret = "dGVzdF90ZXN0X3Rlc3RfdGVzdF90ZXN0X3Rlc3RfdGVzdF90ZXN0X3Rlc3RfdGVzdF90ZXN0X3Rlc3RfdGVzdF8K";
    private long accessTime = 1000L;

    @BeforeEach
    void beforeEach() {
        jwtTokenProvider = new JwtTokenProvider(headerType, issuser, secret, accessTime);
    }

    @Test
    @DisplayName("generateToken()으로 토큰이 생성된다.")
    void generateTokenTest() {

        // setup && given
        when(user.getId()).thenReturn(1L);

        // when
        String result = jwtTokenProvider.generateToken(user);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("입력받은 토큰으로 유저의 Authentication을 반환할 수 있다.")
    void getAuthenticationTest() {

        // given
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        long now = (new Date()).getTime();
        Long id = 2L;
        String accessToken = Jwts.builder()
            .setSubject(id.toString())
            .setExpiration(new Date(now + 300000))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        // when
        JwtAuthentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        // then
        assertThat((Long) authentication.getPrincipal()).isEqualTo(id);
        assertThat(authentication.getCredentials()).isEqualTo(accessToken);
    }

    @Test
    @DisplayName("입력받은 토큰이 만료되면 Authentication을 반환할 수 없고 Exception이 발생해야 한다.")
    void getAuthenticationByExpiredTokenTest() {

        // given
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        long now = (new Date()).getTime();
        Long id = 2L;
        String accessToken = Jwts.builder()
            .setSubject(id.toString())
            .setExpiration(new Date(now - 3000))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        // when & then
        assertThatExceptionOfType(Exception.class)
            .isThrownBy(() -> jwtTokenProvider.getAuthentication(accessToken));
    }

    @Test
    @DisplayName("정상적인 토큰은 true를 반환한다.")
    void testValidateTokenByTrueToken() {

        // given
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        long now = (new Date()).getTime();
        String accessToken = Jwts.builder()
            .setExpiration(new Date(now + 3000))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        // when
        boolean validateion = jwtTokenProvider.validateToken(accessToken);

        // then
        assertTrue(validateion);
    }

    @Test
    @DisplayName("잘못 입력된 토큰은 false를 반환한다.")
    void testValidateTokenByMalFormedToken() {

        // given
        String accessToken = "te.st.";

        // when
        boolean validation = jwtTokenProvider.validateToken(accessToken);

        // then
        assertFalse(validation);
    }

    @Test
    @DisplayName("만료된 토큰은 false를 반환한다.")
    void testValidateTokenByExpiredToken() {

        // given
        long now = (new Date()).getTime();
        String accessToken = Jwts.builder()
            .setExpiration(new Date(now - 100))
            .compact();

        // when
        boolean validation = jwtTokenProvider.validateToken(accessToken);

        // then
        assertFalse(validation);
    }

    @Test
    @DisplayName("지원하지 않는 토큰은 false를 반환한다.")
    void testValidateTokenByUnsupportedToken() {

        // given
        long now = (new Date()).getTime();
        String accessToken = Jwts.builder()
            .claim("auth2", "testauth")
            .setExpiration(new Date(now + 3000))
            .compact();

        // when
        boolean validation = jwtTokenProvider.validateToken(accessToken);

        // then
        assertFalse(validation);
    }

    @Test
    @DisplayName("토큰의 형태가 잘못 들어온 토큰은 false를 반환한다.")
    void testValidateTokenByIllegalArgumentToken() {

        // given
        String accessToken = "";

        // when
        boolean validation = jwtTokenProvider.validateToken(accessToken);

        // then
        assertFalse(validation);
    }

}