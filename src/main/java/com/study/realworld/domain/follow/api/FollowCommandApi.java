package com.study.realworld.domain.follow.api;

import com.study.realworld.domain.follow.application.FollowUserCommandService;
import com.study.realworld.domain.follow.dto.FollowResponse;
import com.study.realworld.domain.follow.dto.UnFollowResponse;
import com.study.realworld.domain.user.domain.vo.UserName;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FollowCommandApi {

    private final FollowUserCommandService followUserCommandService;

    @PostMapping("/profiles/{username}/follow")
    public ResponseEntity<FollowResponse> follow(@AuthenticationPrincipal final Long userId,
                                                 @PathVariable final String username) {
        final FollowResponse followResponse = followUserCommandService.follow(userId, UserName.from(username));
        return ResponseEntity.ok().body(followResponse);
    }

    @DeleteMapping("/profiles/{username}/follow")
    public ResponseEntity<UnFollowResponse> unfollow(@AuthenticationPrincipal final Long userId,
                                                     @PathVariable final String username) {
        final UnFollowResponse unfollowResponse = followUserCommandService.unfollow(userId, UserName.from(username));
        return ResponseEntity.ok().body(unfollowResponse);
    }
}
