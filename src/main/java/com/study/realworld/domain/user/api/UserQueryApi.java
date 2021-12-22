package com.study.realworld.domain.user.api;

import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.dto.UserInfo;
import com.study.realworld.global.common.AccessToken;
import com.study.realworld.global.common.TokenProvider;
import com.study.realworld.global.common.TokenProviderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserQueryApi {

    private final UserQueryService userQueryService;
    private final TokenProvider tokenProvider;

    @GetMapping("/user")
    public ResponseEntity<UserInfo> find(@AuthenticationPrincipal final Long userId) {
        final User user = userQueryService.findById(userId);
        final TokenProviderDto tokenProviderDto = TokenProviderDto.from(user);
        final AccessToken accessToken = tokenProvider.createAccessToken(tokenProviderDto);
        final UserInfo userInfo = UserInfo.fromUserWithToken(user, accessToken);
        return ResponseEntity.ok().body(userInfo);
    }
}
