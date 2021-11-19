package com.study.realworld.domain.auth.api;

import com.study.realworld.domain.auth.application.AuthService;
import com.study.realworld.domain.auth.dto.Login;
import com.study.realworld.global.common.TokenProviderDto;
import com.study.realworld.global.common.AccessToken;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.global.common.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthApi {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @PostMapping("/users/login")
    public ResponseEntity<Login.Response> login(@Valid @RequestBody final Login.Request request) {
        final User user = authService.login(request.userEmail(), request.userPassword());
        final TokenProviderDto tokenProviderDto = TokenProviderDto.from(user);
        final AccessToken accessToken = tokenProvider.createAccessToken(tokenProviderDto);
        final Login.Response response = Login.Response.fromUserWithToken(user, accessToken);
        return ResponseEntity.ok().body(response);
    }
}
