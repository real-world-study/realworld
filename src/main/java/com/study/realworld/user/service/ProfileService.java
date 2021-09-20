package com.study.realworld.user.service;

import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.model.ProfileModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public ProfileModel findProfile(Long loginId, Username username) {
        User user = findById(loginId);
        User followee = findByUsername(username);
        return ProfileModel.fromProfileAndFollowing(user.profile(), user.isFollow(followee));
    }

    @Transactional
    public ProfileModel followUser(Long loginId, Username username) {
        User user = findById(loginId);
        User followee = findByUsername(username);
        user.followingUser(followee);
        return ProfileModel.fromProfileAndFollowing(user.profile(), user.isFollow(followee));
    }

    @Transactional
    public ProfileModel unfollowUser(Long loginId, Username username) {
        User user = findById(loginId);
        User followee = findByUsername(username);
        user.unfollowingUser(followee);
        return ProfileModel.fromProfileAndFollowing(user.profile(), user.isFollow(followee));
    }

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public User findByUsername(Username username) {
        return userRepository.findByProfileUsername(username)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

}
