package com.study.realworld.user.application;

import com.study.realworld.core.domain.user.entity.User;
import com.study.realworld.core.domain.user.repository.UserRepository;
import com.study.realworld.user.application.model.UserLoginModel;
import com.study.realworld.user.application.model.UserRegisterModel;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * @author Jeongjoon Seo
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(UserRegisterModel userRegisterModel) {
        return userRepository.save(userRegisterModel.toUser());
    }

    @Transactional(readOnly = true)
    public User login(UserLoginModel userLoginModel) {
        User findUser = userLoginModel.toUser();
        User user = userRepository.findByEmail(findUser.getEmail())
                                  .orElseThrow(() -> new IllegalArgumentException("invalid email")); // TODO: Exception 커스터마이징

        if (!user.isMatchesPassword(findUser.getPassword(), passwordEncoder)) {
            throw new IllegalArgumentException("invalid password");
        }

        return user;
    }
}
