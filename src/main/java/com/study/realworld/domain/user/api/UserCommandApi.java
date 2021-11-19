package com.study.realworld.domain.user.api;

import com.study.realworld.global.common.TokenProviderDto;
import com.study.realworld.global.common.AccessToken;
import com.study.realworld.domain.user.application.UserCommandService;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.dto.UserUpdate;
import com.study.realworld.domain.user.dto.UserJoin;
import com.study.realworld.global.common.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserCommandApi {

    private final UserCommandService userCommandService;
    private final TokenProvider tokenProvider;

    @PostMapping("/users")
    public ResponseEntity<UserJoin.Response> join(@Valid @RequestBody final UserJoin.Request request) {
        final User user = userCommandService.join(request.toEntity());
        final TokenProviderDto tokenProviderDto = TokenProviderDto.from(user);
        final AccessToken accessToken = tokenProvider.createToken(tokenProviderDto);
        final UserJoin.Response response = UserJoin.Response.fromUserWithToken(user, accessToken);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/users")
    public ResponseEntity<UserUpdate.Response> update(@AuthenticationPrincipal final Long principal,
                                                      @Valid @RequestBody final UserUpdate.Request request) {
        final User user = userCommandService.update(principal, request);
        final TokenProviderDto tokenProviderDto = TokenProviderDto.from(user);
        final AccessToken accessToken = tokenProvider.createToken(tokenProviderDto);
        final UserUpdate.Response response = UserUpdate.Response.fromUserWithToken(user, accessToken);
        return ResponseEntity.ok().body(response);
    }
}
