package com.study.realworld.domain.follow.api;

import com.study.realworld.domain.follow.application.FollowQueryService;
import com.study.realworld.domain.follow.dto.ProfileResponse;
import com.study.realworld.domain.user.domain.vo.UserName;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FollowQueryApi {

    private final FollowQueryService followQueryService;

    @GetMapping("/profiles/{username}")
    public ResponseEntity<ProfileResponse> profile(@AuthenticationPrincipal final Long userId,
                                                    @PathVariable final String username) {
        final ProfileResponse profile = followQueryService.profile(userId, UserName.from(username));
        return ResponseEntity.ok().body(profile);
    }

    @GetMapping("/profiles/querydsl/{username}")
    public ResponseEntity<ProfileResponse> profile2(@AuthenticationPrincipal final Long userId,
                                                   @PathVariable final String username) {
        final ProfileResponse profile = followQueryService.profile2(userId, UserName.from(username));
        return ResponseEntity.ok().body(profile);
    }
}
