package com.tistory.povia.realworld.user.service;

import com.tistory.povia.realworld.user.domain.Email;
import com.tistory.povia.realworld.user.domain.User;
import com.tistory.povia.realworld.user.exception.DuplicatedEmailException;
import com.tistory.povia.realworld.user.repository.UserRepository;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmail(Email email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = false)
    public User join(User user) {
        checkDuplicatedEmail(user.email());

        return userRepository.save(user);
    }

    private void checkDuplicatedEmail(Email email) {
        userRepository
                .findByEmail(email)
                .ifPresent(
                        user -> {
                            throw new DuplicatedEmailException();
                        });
    }
}
