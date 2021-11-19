package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.persist.UserRepository;
import com.study.realworld.domain.user.domain.vo.UserEmail;
import com.study.realworld.domain.user.dto.UserUpdate;
import com.study.realworld.domain.user.error.exception.DuplicatedEmailException;
import com.study.realworld.domain.user.error.exception.IdentityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User join(final User user) {
        validateDuplicatedEmail(user.userEmail());
        user.encode(passwordEncoder);
        return userRepository.save(user);
    }

    public User update(final Long userId, final UserUpdate.Request request) {
        validateDuplicatedEmail(request.memberEmail());
        final User user = findUserById(userId);
        return user.changeEmail(request.memberEmail())
                .changeBio(request.memberBio())
                .changeImage(request.memberImage());
    }

    private User findUserById(final Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new IdentityNotFoundException(userId));
    }

    private void validateDuplicatedEmail(final UserEmail userEmail) {
        if (userRepository.existsByUserEmail(userEmail)) {
            throw new DuplicatedEmailException(userEmail.value());
        }
    }
}
