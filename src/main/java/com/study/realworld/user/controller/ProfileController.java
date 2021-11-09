package com.study.realworld.user.controller;

import com.study.realworld.global.security.CurrentUserId;
import com.study.realworld.user.domain.vo.Username;
import com.study.realworld.user.dto.response.ProfileResponse;
import com.study.realworld.user.service.ProfileService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable String username, @CurrentUserId Long loginId) {
        ProfileResponse response = Optional.ofNullable(loginId)
            .map(id -> profileService.findProfile(id, Username.of(username)))
            .orElse(profileService.findProfile(Username.of(username)));
        return ResponseEntity.ok().body(response);
    }

}
