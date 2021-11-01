package com.study.realworld.follow.controller;

import com.study.realworld.follow.controller.response.FollowResponse;
import com.study.realworld.follow.service.FollowService;
import com.study.realworld.follow.service.model.response.FollowResponseModel;
import com.study.realworld.global.security.CurrentUserId;
import com.study.realworld.user.domain.Username;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/profiles/{username}/follow")
    public ResponseEntity<FollowResponse> follow(@PathVariable String username, @CurrentUserId Long loginId) {
        FollowResponseModel followResponse = followService.followUser(loginId, Username.of(username));
        return ResponseEntity.ok().body(FollowResponse.of(followResponse));
    }

    @DeleteMapping("/profiles/{username}/follow")
    public ResponseEntity<FollowResponse> unfollow(@PathVariable String username, @CurrentUserId Long loginId) {
        FollowResponseModel followResponse = followService.followUser(loginId, Username.of(username));
        return ResponseEntity.ok().body(FollowResponse.of(followResponse));
    }

}
