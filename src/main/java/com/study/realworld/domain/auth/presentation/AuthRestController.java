package com.study.realworld.domain.auth.presentation;

import com.study.realworld.domain.auth.application.AuthLoginService;
import com.study.realworld.domain.auth.dto.LoginRequest;
import com.study.realworld.domain.auth.dto.ResponseToken;
import com.study.realworld.domain.user.domain.Password;
import com.study.realworld.global.config.security.jwt.JwtAuthentication;
import com.study.realworld.domain.auth.infrastructure.TokenProvider;
import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.study.realworld.domain.user.domain.User.DEFAULT_AUTHORITY;

@RestController
public class AuthRestController {

    private final AuthLoginService authLoginService;
    private final TokenProvider tokenProvider;

    public AuthRestController(final AuthLoginService authLoginService,
                              final TokenProvider tokenProvider) {
        this.authLoginService = authLoginService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/api/users/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody final LoginRequest request) {
        final User user = authLoginService.login(request.email(), request.password());
        final ResponseToken token = responseToken(user);
        final UserResponse userResponse = UserResponse.fromUserWithToken(user, token);
        return ResponseEntity.ok().body(userResponse);
    }

    private ResponseToken responseToken(final User user) {
        final UserDetails userDetails = mapToSecurityUser(user);
        final JwtAuthentication jwtAuthentication = JwtAuthentication.ofUserDetails(userDetails);
        return tokenProvider.createToken(jwtAuthentication);
    }

    private UserDetails mapToSecurityUser(final User user) {
        final Email securityUsername = user.email();
        final Password password = user.password();
        return org.springframework.security.core.userdetails.User.builder()
                .username(securityUsername.email())
                .password(password.password())
                .authorities(DEFAULT_AUTHORITY)
                .build();
    }

}
