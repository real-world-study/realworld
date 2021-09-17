package com.study.realworld.security;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.global.exception.JwtException;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JjwtServiceTest {

    @Mock
    private User user;

    @Mock
    private UserRepository userRepository;

    private JjwtService jwtTokenProvider;

    private String headerType = "headerType";
    private String issuser = "issuer";
    private String secret = "dGVzdF90ZXN0X3Rlc3RfdGVzdF90ZXN0X3Rlc3RfdGVzdF90ZXN0X3Rlc3RfdGVzdF90ZXN0X3Rlc3RfdGVzdF8K";
    private long accessTime = 1000L;

    @BeforeEach
    void beforeEach() {
        jwtTokenProvider = new JjwtService(headerType, issuser, secret, accessTime, userRepository);
    }

    @Test
    @DisplayName("generateToken()으로 토큰이 생성된다.")
    void createTokenTest() {

        // setup && given
        when(user.id()).thenReturn(1L);

        // when
        String result = jwtTokenProvider.createToken(user);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("입력받은 토큰으로 유저를 반환할 수 있다.")
    void getUserTest() {

        // setup
        User user = User.Builder().build();
        when(userRepository.findById(2L)).thenReturn(ofNullable(user));

        // given
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        long now = (new Date()).getTime();
        Long id = 2L;
        String accessToken = Jwts.builder()
            .setSubject(id.toString())
            .setExpiration(new Date(now + 300_000))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        // when
        Optional<User> result = jwtTokenProvider.getUser(accessToken);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(ofNullable(user));
    }

    @Test
    @DisplayName("입력받은 토큰이 만료되면 Authentication을 반환할 수 없고 Exception이 발생해야 한다.")
    void getUserByExpiredTokenTest() {

        // given
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        long now = (new Date()).getTime();
        Long id = 2L;
        String accessToken = Jwts.builder()
            .setSubject(id.toString())
            .setExpiration(new Date(now - 3_000))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        // when & then
        assertThatExceptionOfType(JwtException.class)
            .isThrownBy(() -> jwtTokenProvider.getUser(accessToken))
            .withMessageMatching(ErrorCode.INVALID_EXPIRED_JWT.getMessage());
    }

    @Test
    @DisplayName("잘못 입력된 토큰은 잘못된 JWT 서명 Excpetion을 반환한다.")
    void testValidateTokenByMalFormedToken() {

        // given
        String accessToken = "te.st.";

        // when & then
        assertThatExceptionOfType(JwtException.class)
            .isThrownBy(() -> jwtTokenProvider.getUser(accessToken))
            .withMessageMatching(ErrorCode.INVALID_MALFORMED_JWT.getMessage());
    }

    @Test
    @DisplayName("만료된 토큰은 만료된 JWT 서명 Exception을 반환한다.")
    void testValidateTokenByExpiredToken() {

        // given
        long now = (new Date()).getTime();
        String accessToken = Jwts.builder()
            .setExpiration(new Date(now - 100))
            .compact();

        // when & then
        assertThatExceptionOfType(JwtException.class)
            .isThrownBy(() -> jwtTokenProvider.getUser(accessToken))
            .withMessageMatching(ErrorCode.INVALID_EXPIRED_JWT.getMessage());
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

        // when & then
        assertThatExceptionOfType(JwtException.class)
            .isThrownBy(() -> jwtTokenProvider.getUser(accessToken))
            .withMessageMatching(ErrorCode.INVALID_UNSUPPORTED_JWT.getMessage());
    }

    @Test
    @DisplayName("토큰의 형태가 잘못 들어온 토큰은 false를 반환한다.")
    void testValidateTokenByIllegalArgumentToken() {

        // given
        String accessToken = "";

        // when & then
        assertThatExceptionOfType(JwtException.class)
            .isThrownBy(() -> jwtTokenProvider.getUser(accessToken))
            .withMessageMatching(ErrorCode.INVALID_ILLEGAL_ARGUMENT_JWT.getMessage());
    }

}