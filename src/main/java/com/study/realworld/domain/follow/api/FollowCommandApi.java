package com.study.realworld.domain.follow.api;

import com.study.realworld.domain.follow.application.FollowCommandService;
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

    private final FollowCommandService followCommandService;

    @PostMapping("/profiles/{username}/follow")
    public ResponseEntity<FollowResponse> profile2(@AuthenticationPrincipal final Long userId,
                                                   @PathVariable final String username) {
        final FollowResponse followResponse = followCommandService.follow(userId, UserName.from(username));
        return ResponseEntity.ok().body(followResponse);
    }

    @DeleteMapping("/profiles2/{username}/follow")
    public ResponseEntity<UnFollowResponse> profile(@AuthenticationPrincipal final Long userId,
                                                    @PathVariable final String username) {
        final UnFollowResponse unfollowResponse = followCommandService.unfollow(userId, UserName.from(username));
        return ResponseEntity.ok().body(unfollowResponse);
    }
}
