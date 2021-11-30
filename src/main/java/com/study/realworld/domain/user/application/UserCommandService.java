package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.persist.UserRepository;
import com.study.realworld.domain.user.domain.vo.UserEmail;
import com.study.realworld.domain.user.dto.UserJoin;
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

    public User join(final UserJoin.Request request) {
        final User user = request.toEntity(passwordEncoder);
        validateDuplicatedEmail(user.userEmail());
        return userRepository.save(user);
    }

    public User update(final Long userId, final UserUpdate.Request request) {
        validateDuplicatedAndSameAsEmail(userId, request.memberEmail());
        final User user = findUserById(userId);
        return user.changeEmail(request.memberEmail())
                .changeBio(request.memberBio())
                .changeImage(request.memberImage());
    }

    public void delete(final Long userId) {
        final User user = findUserById(userId);
        user.delete();
    }

    private User findUserById(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IdentityNotFoundException(userId));
    }

    private void validateDuplicatedEmail(final UserEmail userEmail) {
        if (userRepository.existsByUserEmail(userEmail)) {
            throw new DuplicatedEmailException(userEmail.userEmail());
        }
    }

    private void validateDuplicatedAndSameAsEmail(final Long userId, final UserEmail userEmail) {
        final User user = findUserById(userId);
        if (!user.isSameAsUserEmail(userEmail) && userRepository.existsByUserEmail(userEmail)) {
            throw new DuplicatedEmailException(userEmail.userEmail());
        }
    }
}
