package com.study.realworld.user.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;

@ExtendWith(MockitoExtension.class)
class TokenProviderTest {

    private TokenProvider tokenProvider;

    {
        String secretKey = "c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LWRvbHBoYWdvLXJlYWx3b3JsZC1wcm9qZWN0LWJhY2tlbmQtc3ByaW5nLWJvb3Qtc2VjdXJpdHktZG9scGhhZ28K";
        tokenProvider = new TokenProvider(secretKey);
    }

    @Test
    void token_create_validate_parse() {
        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("dolphago@test.net", "1q2w3e4r");
        final String token = tokenProvider.createToken(usernamePasswordAuthenticationToken);

        final String userEmail = tokenProvider.getUserEmail(token);
        assertAll(
                () -> assertThat(userEmail).isEqualTo(usernamePasswordAuthenticationToken.getPrincipal()),
                () -> assertDoesNotThrow(() -> tokenProvider.validateToken(token))
        );
    }

}
