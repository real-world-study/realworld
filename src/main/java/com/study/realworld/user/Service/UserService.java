package com.study.realworld.user.Service;

import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.study.realworld.user.domain.Password.encode;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User join(User user) {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(param -> {
                    throw new RuntimeException("already user username");
                });
        userRepository.findByEmail(user.getEmail())
                .ifPresent(param -> {
                    throw new RuntimeException("already user email");
                });

        return userRepository.save(
                new User.Builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(encode(user.getPassword().getPassword(), passwordEncoder))
                    .bio(user.getBio())
                    .image(user.getImage())
                    .build()
        );
    }

}
