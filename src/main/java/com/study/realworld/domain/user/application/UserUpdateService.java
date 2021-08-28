package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class UserUpdateService {

    private final UserRepository userRepository;

    public UserUpdateService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 중간 모델 도입할지 의문
    public User update(final User user) { // ??
        final User findUser = findUserByEmail(user);
        return findUser; // 다른 테스트 먼저
    }

    private User findUserByEmail(final User user) {
        return userRepository.findByEmail(user.email())
                .orElseThrow(IllegalArgumentException::new);
    }

}
