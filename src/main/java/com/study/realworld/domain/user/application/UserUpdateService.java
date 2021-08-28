package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class UserUpdateService {

    private final UserRepository userRepository;

    public UserUpdateService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User update(final User updateRequestUser, final Email userEmail) {
        final User user = findUserByEmail(userEmail);
        final Email changeEmail = updateRequestUser.email();
        validateDuplicatedEmail(changeEmail);
        return user.changeEmail(changeEmail)
                .changeBio(updateRequestUser.bio())
                .changeImage(updateRequestUser.image());
    }

    private void validateDuplicatedEmail(final Email email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException();
        }
    }

    private User findUserByEmail(final Email email) {
        return userRepository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new);
    }

}
