package com.study.realworld.config.auth;

import com.study.realworld.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtProviderTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String BIO = "bio";
    private static final String IMAGE = "image";

    private JwtProvider jwtProvider;

    @BeforeEach
    void beforeEach() {
        jwtProvider = new JwtProvider();
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
        boolean isValidToken = jwtProvider.isValidToken(token);

        // then
        assertAll(
                () -> assertThat(token).isNotNull(),
                () -> assertThat(subject).isEqualTo(EMAIL),
                () -> assertThat(isValidToken).isTrue()
        );
    }

}
