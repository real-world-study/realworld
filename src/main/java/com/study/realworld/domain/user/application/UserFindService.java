package com.study.realworld.domain.user.application;

import com.study.realworld.domain.follow.domain.FollowRepository;
import com.study.realworld.domain.user.domain.vo.Email;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.persist.UserRepository;
import com.study.realworld.domain.user.error.exception.EmailNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class UserFindService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public UserFindService(final UserRepository userRepository, final FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    public User findUserByEmail(final String email) {
        return findByEmail(email);
    }

//    public User findProfileByName(final Long id, final Name username) {
//        final User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
//        return findByEmail();
//    }

    private User findByEmail(final String email) {
        return userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new EmailNotFoundException(email));
    }

}
