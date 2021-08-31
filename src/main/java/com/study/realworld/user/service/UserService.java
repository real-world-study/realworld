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
        checkDuplicatedByUsername(user.usesrname());
        checkDuplicatedByEmail(user.email());

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

        updateUser.getUsername().ifPresent(username -> checkDuplicatedUsernameAndChangeUsername(user, username));
        updateUser.getEmail().ifPresent(email -> checkDuplicatedEmailAndChangeEmail(user, email));
        updateUser.getPassword().ifPresent(password -> user.changePassword(password, passwordEncoder));
        updateUser.getBio().ifPresent(user::changeBio);
        updateUser.getImage().ifPresent(user::changeImage);

        return user;
    }

    private void checkDuplicatedByUsername(Username username) {
        findByUsername(username).ifPresent(param -> {
            throw new RuntimeException("already user username");
        });
    }

    private void checkDuplicatedByEmail(Email email) {
        findByEmail(email).ifPresent(param -> {
            throw new RuntimeException("already user email");
        });
    }

    private Optional<User> findByUsername(Username username) {
        return userRepository.findByUsername(username);
    }

    private Optional<User> findByEmail(Email email) {
        return userRepository.findByEmail(email);
    }

    private void checkDuplicatedEmailAndChangeEmail(User user, Email email) {
        checkDuplicatedByEmail(email);
        user.changeEmail(email);
    }

    private void checkDuplicatedUsernameAndChangeUsername(User user, Username username) {
        checkDuplicatedByUsername(username);
        user.changeUsername(username);
    }

}
