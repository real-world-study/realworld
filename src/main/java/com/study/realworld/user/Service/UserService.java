package com.study.realworld.user.Service;

import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import com.study.realworld.user.domain.Username;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

import static com.study.realworld.user.domain.Password.encode;

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
    public User join(@Valid User input) {
        checkOverlapByUsernameAndEmail(input.getUsername(), input.getEmail());

        User user = new User.Builder(input)
            .password(encode(input.getPassword().getPassword(), passwordEncoder))
            .build();
        return userRepository.save(user);
    }

    private void checkOverlapByUsernameAndEmail(Username username, Email email) {
        userRepository.findByUsername(username)
            .ifPresent(param -> {
                throw new RuntimeException("already user username");
            });
        userRepository.findByEmail(email)
            .ifPresent(param -> {
                throw new RuntimeException("already user email");
            });
    }

}
