package com.study.realworld.user.web;

import com.study.realworld.config.auth.JwtProvider;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.dto.UserJoinRequest;
import com.study.realworld.user.dto.UserLoginRequest;
import com.study.realworld.user.dto.UserResponse;
import com.study.realworld.user.dto.UserUpdateRequest;
import com.study.realworld.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

    private final JwtProvider jwtProvider;
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserResponse> joinUser(@RequestBody UserJoinRequest request) {
        User user = userService.save(request);
        String token = jwtProvider.generateJwtToken(user);

        return ResponseEntity.ok()
                .body(UserResponse.of(user, token));
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserLoginRequest request) {
        User user = userService.login(request);
        String token = jwtProvider.generateJwtToken(user);

        return ResponseEntity.ok()
                .body(UserResponse.of(user, token));
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal String principal) {
        User user = userService.findByEmail(principal);
        String token = jwtProvider.generateJwtToken(user);

        return ResponseEntity.ok()
                .body(UserResponse.of(user, token));
    }

    @PutMapping("/user")
    public ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal String principal,
                                                   @RequestBody UserUpdateRequest request) {
        User user = userService.update(request, principal);
        String token = jwtProvider.generateJwtToken(user);

        return ResponseEntity.ok()
                .body(UserResponse.of(user, token));
    }

}
