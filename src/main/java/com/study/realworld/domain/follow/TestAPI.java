package com.study.realworld.domain.follow;

import com.study.realworld.domain.follow.application.FollowService;
import com.study.realworld.domain.follow.domain.FollowQueryDslRepository;
import com.study.realworld.domain.follow.dto.FollowResponse;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.persist.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional(readOnly = true)
@RestController
public class TestAPI {
    private final FollowQueryDslRepository queryDslRepository;
    private final UserRepository userRepository;
    private final FollowService followService;

    public TestAPI(final FollowQueryDslRepository queryDslRepository, final UserRepository userRepository, final FollowService followService) {
        this.queryDslRepository = queryDslRepository;
        this.userRepository = userRepository;
        this.followService = followService;
    }

    @PostMapping("follow/{targetId}")
    public ResponseEntity<FollowResponse> follow(@AuthenticationPrincipal Long id, @PathVariable Long targetId) {
        User user = userRepository.findById(id).get();
        User user1 = userRepository.findById(targetId).get();
        followService.following(id, targetId);
        FollowResponse followResponse = queryDslRepository.existMeAndFollowing(user, user1);
        return ResponseEntity.ok().body(followResponse);
    }

    @GetMapping("test/{targetId}")
    public ResponseEntity<FollowResponse> test(@AuthenticationPrincipal Long id, @PathVariable Long targetId) {
        User user = userRepository.findById(id).get();
        User user1 = userRepository.findById(targetId).get();
        FollowResponse followResponse = queryDslRepository.existMeAndFollowing(user, user1);
        return ResponseEntity.ok().body(followResponse);
    }

}
