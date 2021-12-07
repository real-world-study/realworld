package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.persist.UserQuerydsl;
import com.study.realworld.domain.user.domain.persist.UserRepository;
import com.study.realworld.domain.user.domain.vo.UserEmail;
import com.study.realworld.domain.user.domain.vo.UserName;
import com.study.realworld.domain.user.error.exception.EmailNotFoundException;
import com.study.realworld.domain.user.error.exception.IdentityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserQueryService {

    private final UserQuerydsl userQuerydsl;

    public User findById(final Long userId) {
        return userQuerydsl.findById(userId)
                .orElseThrow(() -> new IdentityNotFoundException(userId));
    }

    public User findByMemberEmail(final UserEmail userEmail) {
        return userQuerydsl
                .findByUserEmail(userEmail)
                .orElseThrow(() -> new EmailNotFoundException(userEmail.userEmail()));
    }

    public User findByUserName(final UserName userName) {
        return userQuerydsl
                .findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException());
    }
}
