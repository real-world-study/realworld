package com.study.realworld.user.service;

import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.model.UserUpdateModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        checkDuplicatedByUsername(user.username());
        checkDuplicatedByEmail(user.email());

        user.encodePassword(passwordEncoder);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User login(Email email, Password password) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.login(password, passwordEncoder);
        return user;
    }

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public User findByUsername(Username username) {
        return userRepository.findByProfileUsername(username)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public User update(UserUpdateModel updateUser, Long userId) {
        User user = findById(userId);

        updateUser.getUsername()
            .filter(username -> !user.username().equals(username))
            .ifPresent(username -> checkDuplicatedUsernameAndChangeUsername(user, username));
        updateUser.getEmail()
            .filter(email -> !user.email().equals(email))
            .ifPresent(email -> checkDuplicatedEmailAndChangeEmail(user, email));
        updateUser.getPassword().ifPresent(password -> user.changePassword(password, passwordEncoder));
        updateUser.getBio().ifPresent(user::changeBio);
        updateUser.getImage().ifPresent(user::changeImage);

        return user;
    }

    private void checkDuplicatedByUsername(Username username) {
        userRepository.findByProfileUsername(username).ifPresent(param -> {
            throw new BusinessException(ErrorCode.USERNAME_DUPLICATION);
        });
    }

    private void checkDuplicatedByEmail(Email email) {
        userRepository.findByEmail(email).ifPresent(param -> {
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATION);
        });
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
