package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.persist.UserRepository;
import com.study.realworld.domain.user.domain.vo.UserEmail;
import com.study.realworld.domain.user.dto.UserUpdate;
import com.study.realworld.domain.user.error.exception.DuplicatedEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserCommandService {

    private final UserQueryService userQueryService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User join(final User user) {
        validateDuplicatedEmail(user.userEmail());
        user.encode(passwordEncoder);
        return userRepository.save(user);
    }

    public User update(final Long userId, final UserUpdate.Request request) {
        validateDuplicatedAndSameAsEmail(userId, request.memberEmail());
        final User user = userQueryService.findById(userId);
        return user.changeEmail(request.memberEmail())
                .changeBio(request.memberBio())
                .changeImage(request.memberImage());
    }

    private void validateDuplicatedAndSameAsEmail(final Long userId, final UserEmail userEmail) {
        final User me = userQueryService.findById(userId);
        if (!me.isSameAsUserEmail(userEmail) && userRepository.existsByUserEmail(userEmail)) {
            throw new DuplicatedEmailException(userEmail.value());
        }
    }

    public void delete(final Long userId) {
        final User me = userQueryService.findById(userId);
        me.delete();
    }

    private void validateDuplicatedEmail(final UserEmail userEmail) {
        if (userQueryService.existsByUserEmail(userEmail)) {
            throw new DuplicatedEmailException(userEmail.value());
        }
    }
}
