package com.tistory.povia.realworld.auth.service;

import com.tistory.povia.realworld.auth.controller.AuthRequest;
import com.tistory.povia.realworld.auth.controller.AuthResponse;
import com.tistory.povia.realworld.auth.exception.AuthException;
import com.tistory.povia.realworld.auth.infra.JwtTokenProvider;
import com.tistory.povia.realworld.user.domain.Email;
import com.tistory.povia.realworld.user.domain.User;
import com.tistory.povia.realworld.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthResponse login(AuthRequest auth) {
        User user = findMember(auth.email());

        user.checkSamePassword(auth.password());
        String token = jwtTokenProvider.createToken(auth.email());
        return AuthResponse.fromUserAndToken(user, token);
    }

    public AuthResponse findByToken(String credentials) {
        if (!jwtTokenProvider.validateToken(credentials)) {
            throw new AuthException();
        }

        String address = jwtTokenProvider.getPayload(credentials);
        User user = findMember(address);

        return AuthResponse.fromUserAndToken(user, credentials);
    }

    public User findMember(String address) {
        Email email = new Email(address);
        User user = userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);

        return user;
    }
}
