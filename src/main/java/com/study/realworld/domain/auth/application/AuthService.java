package com.study.realworld.domain.auth.application;

import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.UserEmail;
import com.study.realworld.domain.user.domain.vo.UserPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final UserQueryService userQueryService;
    private final PasswordEncoder passwordEncoder;

    public User login(final UserEmail userEmail, final UserPassword userPassword) {
        final User user = userQueryService.findByMemberEmail(userEmail);
        return user.login(userPassword, passwordEncoder);
    }
}
