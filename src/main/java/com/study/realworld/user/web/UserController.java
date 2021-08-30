package com.study.realworld.user.web;

import com.study.realworld.config.auth.JwtProvider;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.dto.UserJoinRequest;
import com.study.realworld.user.dto.UserLoginRequest;
import com.study.realworld.user.dto.UserResponse;
import com.study.realworld.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

        return ResponseEntity.ok()
                .body(UserResponse.of(user, "token"));
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserLoginRequest request) {
        User user = userService.login(request);
        String token = jwtProvider.generateJwtToken(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Token " + token)
                .body(UserResponse.of(user, token));
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getUser(HttpServletRequest servletRequest) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByEmail(email);
        String token = servletRequest.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];

        return ResponseEntity.ok()
                .body(UserResponse.of(user, token));
    }

}
