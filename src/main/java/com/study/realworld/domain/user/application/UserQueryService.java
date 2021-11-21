package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.persist.UserRepository;
import com.study.realworld.domain.user.domain.vo.UserEmail;
import com.study.realworld.domain.user.domain.vo.UserName;
import com.study.realworld.domain.user.error.exception.EmailNotFoundException;
import com.study.realworld.domain.user.error.exception.IdentityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserQueryService {

    private final UserRepository userRepository;

    public User findById(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IdentityNotFoundException(userId));
    }

    public User findByMemberEmail(final UserEmail userEmail) {
        return userRepository
                .findByUserEmail(userEmail)
                .orElseThrow(() -> new EmailNotFoundException(userEmail.value()));
    }

    public User findByUserName(final UserName userName) {
        return userRepository
                .findByUserName(userName)
                .orElseThrow(() -> new IdentityNotFoundException(1L));
    }

    public Boolean existsByUserEmail(final UserEmail userEmail) {
        return userRepository.existsByUserEmail(userEmail);
    }

}
