package com.study.realworld.config.auth;

import com.study.realworld.user.domain.User;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class JwtProviderTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "email";
    private static final String TOKEN = "token";
    private static final String WRONG_TOKEN = "wrong-token";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String BIO = "bio";
    private static final String IMAGE = "image";

    private static final String secret = "the-key-size-must-be-greater-then-or-equal-to-the-has-output-size-which-is-512-bits-for-the-HS512-algorithm-security-so-called-JWT-JWA-specification";
    private static final int accessTime = 60 * 1000;

    private JwtProvider jwtProvider;

    @BeforeEach
    void beforeEach() {
        jwtProvider = new JwtProvider(secret, accessTime);
    }

    @Test
    void JwtProviderTest() {
        // given
        final User user = User.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        // when
        String token = jwtProvider.generateJwtToken(user);
        String subject = jwtProvider.getSubjectFromToken(token);

        // then
        assertAll(
                () -> assertThat(token).isNotNull(),
                () -> assertThat(subject).isEqualTo(EMAIL)
        );
    }

    @Test
    void getClaimsFromToken_exception() {
        // given

        // when

        // then
        assertThrows(JwtException.class, () -> jwtProvider.getClaimsFromToken(WRONG_TOKEN));
    }

}
