package com.study.realworld.user.presentation;

import com.study.realworld.core.domain.user.entity.User;
import com.study.realworld.core.jwt.TokenProvider;
import com.study.realworld.core.util.SecurityUtil;
import com.study.realworld.user.application.UserService;
import com.study.realworld.user.presentation.model.UserLoginRequest;
import com.study.realworld.user.presentation.model.UserRegisterRequest;
import com.study.realworld.user.presentation.model.UserResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

/**
 * @author Jeongjoon Seo
 */
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping(value = "/users")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        User user = userService.register(request.toModel());
        return ResponseEntity.ok().body(UserResponse.createResponse(user, tokenProvider.createToken(user)));
    }

    @PostMapping(value = "/users/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody UserLoginRequest request) {
        User user = userService.login(request.toModel());
        return ResponseEntity.ok().body(UserResponse.createResponse(user, tokenProvider.createToken(user)));
    }

    @GetMapping(value = "/user")
    public ResponseEntity<UserResponse> getLoginUser(@AuthenticationPrincipal Long userId) {
        User user = userService.getLoginUser(userId);
        return ResponseEntity.ok().body(UserResponse.createResponse(user, SecurityUtil.getAccessToken()));
    }
}
