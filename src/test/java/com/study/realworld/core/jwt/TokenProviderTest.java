package com.study.realworld.core.jwt;

import com.study.realworld.core.domain.user.entity.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author JeongJoon Seo
 */
@ExtendWith(MockitoExtension.class)
class TokenProviderTest {

    private TokenProvider tokenProvider;

    @BeforeEach
    void beforeEach() {
        String secret = "dGVzdF90ZXN0X3Rlc3RfdGVzdF90ZXN0X3Rlc3RfdGVzdF90ZXN0X3Rlc3RfdGVzdF90ZXN0X3Rlc3RfdGVzdF8K";
        long tokenValidityInMilliseconds = 1800;
        tokenProvider = new TokenProvider(secret, tokenValidityInMilliseconds);
    }

    @Test
    void createTokenTest() {

        // given
        User user = User.builder().id(1L).build();

        // when
        String token = tokenProvider.createToken(user);

        // then
        assertThat(token).isNotNull();
    }

}
