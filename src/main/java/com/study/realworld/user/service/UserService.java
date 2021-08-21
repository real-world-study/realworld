package com.study.realworld.user.service;

import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.model.UserUpdateModel;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

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
        checkDuplicatedByUsername(user.getUsername());
        checkDuplicatedByEmail(user.getEmail());

        user.encodePassword(passwordEncoder);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User login(@Valid Email email, @Valid Password password) {
        User user = findByEmail(email).orElseThrow(RuntimeException::new);
        user.login(password, passwordEncoder);
        return user;
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public User update(UserUpdateModel updateUser, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);

        updateUser.getUsername()
            .filter(username -> !user.getUsername().equals(username))
            .ifPresent(
                username -> {
                    checkDuplicatedByUsername(username);
                    user.changeUsername(username);
                });
        updateUser.getEmail()
            .filter(email -> !user.getEmail().equals(email))
            .ifPresent(
                email -> {
                    checkDuplicatedByEmail(email);
                    user.changeEmail(email);
                });
        updateUser.getPassword()
            .filter(
                password -> !passwordEncoder
                    .matches(password.getPassword(), user.getPassword().getPassword()))
            .ifPresent(
                password -> {
                    validByPassword(password);
                    user.changePassword(Password.encode(password, passwordEncoder));
                });
        updateUser.getBio()
            .filter(bio -> !user.getBio().equals(bio))
            .ifPresent(user::changeBio);
        updateUser.getImage()
            .filter(image -> !user.getImage().equals(image))
            .ifPresent(user::changeImage);

        return user;
    }

    private void validByPassword(@Valid Password password) {
    }

    private void checkDuplicatedByUsername(@Valid Username username) {
        findByUsername(username)
            .ifPresent(param -> {
                throw new RuntimeException("already user username");
            });
    }

    private void checkDuplicatedByEmail(@Valid Email email) {
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
