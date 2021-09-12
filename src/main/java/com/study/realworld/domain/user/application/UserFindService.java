package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.domain.UserRepository;
import com.study.realworld.domain.user.error.exception.EmailNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class UserFindService {

    private final UserRepository userRepository;

    public UserFindService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByEmail(final String email) {
        return findByEmail(email);
    }

    private User findByEmail(final String email) {
        return userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new EmailNotFoundException(email));
    }

}
