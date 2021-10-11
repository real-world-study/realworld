package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.vo.Email;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.persist.UserRepository;
import com.study.realworld.domain.user.error.exception.DuplicatedEmailException;
import com.study.realworld.domain.user.error.exception.IdentityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class UserUpdateService {

    private final UserRepository userRepository;

    public UserUpdateService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User update(final User updateRequestUser, final Long principal) {
        final User user = findUserByEmail(principal);
        final Email email = updateRequestUser.email();
        validateDuplicatedEmail(email);
        return user.changeEmail(email)
                .changeBio(updateRequestUser.bio())
                .changeImage(updateRequestUser.image());
    }

    private User findUserByEmail(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IdentityNotFoundException(id));
    }

    private void validateDuplicatedEmail(final Email email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException(email.email());
        }
    }

}
