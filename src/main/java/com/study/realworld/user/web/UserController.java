package com.study.realworld.user.web;

import com.study.realworld.user.domain.User;
import com.study.realworld.user.dto.UserJoinRequest;
import com.study.realworld.user.dto.UserLoginRequest;
import com.study.realworld.user.dto.UserResponse;
import com.study.realworld.user.exception.UserNotFoundException;
import com.study.realworld.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserResponse> joinUser(@RequestBody UserJoinRequest request) {
        User userRequest = UserJoinRequest.toUser(request);
        userRequest.encodePassword(passwordEncoder);

        User user = userService.save(userRequest);
        String token = null;

        return ResponseEntity.ok().body(UserResponse.of(user, token));
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserLoginRequest request) {
        User user = userService.findByEmail(request.getEmail());
        String token = null;

        if(!user.matchesPassword(request.getPassword(), passwordEncoder)) {
            throw new UserNotFoundException(request.getPassword() + " wrong wrong wrong triple wrong");
        }

        return ResponseEntity.ok().body(UserResponse.of(user, token));
    }

}
