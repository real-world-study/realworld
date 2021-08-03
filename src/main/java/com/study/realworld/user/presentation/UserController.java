package com.study.realworld.user.presentation;

import com.study.realworld.core.domain.user.entity.User;
import com.study.realworld.user.application.UserService;
import com.study.realworld.user.presentation.model.UserRegisterRequest;
import com.study.realworld.user.presentation.model.UserResponse;

import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/users")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        User user = userService.register(request.toModel());
        return ResponseEntity.ok().body(UserResponse.createResponse(user));
    }

}
