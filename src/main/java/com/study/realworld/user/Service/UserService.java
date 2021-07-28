package com.study.realworld.user.Service;

import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import com.study.realworld.user.domain.Username;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User join(@Valid User user) {
        checkDuplicatedByUsernameOrEmail(user.getUsername(), user.getEmail());

        user.encodePassword(passwordEncoder);
        return userRepository.save(user);
    }

    private void checkDuplicatedByUsernameOrEmail(Username username, Email email) {
        findByUsername(username)
            .ifPresent(param -> {
                throw new RuntimeException("already user username");
            });
        findByEmail(email)
            .ifPresent(param -> {
                throw new RuntimeException("already user email");
            });
    }

    private Optional<User> findByUsername(Username username) {
        return userRepository.findByUsername(username);
    }

    private Optional<User> findByEmail(Email email) {
        return userRepository.findByEmail(email);
    }

}
