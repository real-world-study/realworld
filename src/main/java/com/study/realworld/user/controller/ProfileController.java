package com.study.realworld.user.controller;

import com.study.realworld.user.controller.response.ProfileResponse;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.ProfileService;
import com.study.realworld.user.service.model.ProfileModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profiles/{username}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable String username, @AuthenticationPrincipal Long loginId) {
        ProfileModel profileModel = profileService.findProfile(loginId, Username.of(username));
        return ResponseEntity.ok().body(ProfileResponse.ofProfileModel(profileModel));
    }

    @PostMapping("/profiles/{username}/follow")
    public ResponseEntity<ProfileResponse> follow(@PathVariable String username, @AuthenticationPrincipal Long loginId) {
        ProfileModel profileModel = profileService.followUser(loginId, Username.of(username));
        return ResponseEntity.ok().body(ProfileResponse.ofProfileModel(profileModel));
    }

    @DeleteMapping("/profiles/{username}/follow")
    public ResponseEntity<ProfileResponse> unfollow(@PathVariable String username, @AuthenticationPrincipal Long loginId) {
        ProfileModel profileModel = profileService.unfollowUser(loginId, Username.of(username));
        return ResponseEntity.ok().body(ProfileResponse.ofProfileModel(profileModel));
    }

}
