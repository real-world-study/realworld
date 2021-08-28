package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Consumer;

@Transactional(readOnly = true)
@Service
public class UserUpdateService {

    private final UserRepository userRepository;

    public UserUpdateService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 중간 모델 도입할지 의문 -> 일단 구현 후 리팩토링 
    // jwt 에서 유저 값 먼저 찾느게 우선 -> updateModel. Email 따로 넘겨주는 식으로
    public User update(final User updateRequestUser, final Email email) {
        final User user = findUserByEmail(email);
        return user.changeEmail(updateRequestUser.email())
                .changeBio(updateRequestUser.bio())
                .changeImage(updateRequestUser.image());
    }

    private User findUserByEmail(final Email email) {
        return userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
    }


}
