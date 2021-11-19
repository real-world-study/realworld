package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.persist.UserRepository;
import com.study.realworld.domain.user.domain.vo.UserEmail;
import com.study.realworld.domain.user.error.exception.EmailNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserQueryService {

    private final UserRepository userRepository;

    public User findByMemberEmail(final UserEmail userEmail) {
        return userRepository
                .findByUserEmail(userEmail)
                .orElseThrow(() -> new EmailNotFoundException(userEmail.value()));
    }
}
