package com.study.realworld.domain.user.api;

import com.study.realworld.domain.user.application.UserCommandService;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.dto.UserJoin;
import com.study.realworld.domain.user.dto.UserUpdate;
import com.study.realworld.global.common.AccessToken;
import com.study.realworld.global.common.TokenProvider;
import com.study.realworld.global.common.TokenProviderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
        final AccessToken accessToken = tokenProvider.createAccessToken(tokenProviderDto);
        final UserJoin.Response response = UserJoin.Response.fromUserWithToken(user, accessToken);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/users")
    public ResponseEntity<UserUpdate.Response> update(@AuthenticationPrincipal final Long userId,
                                                      @Valid @RequestBody final UserUpdate.Request request) {
        final User user = userCommandService.update(userId, request);
        final TokenProviderDto tokenProviderDto = TokenProviderDto.from(user);
        final AccessToken accessToken = tokenProvider.createAccessToken(tokenProviderDto);
        final UserUpdate.Response response = UserUpdate.Response.fromUserWithToken(user, accessToken);
        return ResponseEntity.ok().body(response);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/users")
    public void delete(@Valid @AuthenticationPrincipal final Long userId) {
        userCommandService.delete(userId);
    }
}
