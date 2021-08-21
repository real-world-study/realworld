package com.study.realworld.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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
class JwtProviderTest {

    private JwtProvider jwtProvider;

    private String secret = "dGVzdF90ZXN0X3Rlc3RfdGVzdF90ZXN0X3Rlc3RfdGVzdF90ZXN0X3Rlc3RfdGVzdF90ZXN0X3Rlc3RfdGVzdF8K";

    @BeforeEach
    void beforeEach() {
        jwtProvider = new JwtProvider(secret);
    }

    @Test
    void createTokenTest() {

        // given
        User user = User.builder().id(1L).build();

        // when
        String result = jwtProvider.createToken(user);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void getUserIdSucessTest() throws Exception {

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
        Long result = jwtProvider.getUserId(accessToken);

        // then
        assertThat(result).isEqualTo(2L);
    }

    @Test
    void getUserIdFailTest() {

        // given
        String accessToken = "failToken";

        // when & then
        assertThatExceptionOfType(Exception.class)
            .isThrownBy(() -> jwtProvider.getUserId(accessToken));

    }
}