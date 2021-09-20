package com.study.realworld.user.service;

import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.model.ProfileModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final UserService userService;

    public ProfileService(UserService userService) {
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public ProfileModel findProfile(Long loginId, Username username) {
        User user = userService.findById(loginId);
        User followee = userService.findByUsername(username);
        return ProfileModel.fromProfileAndFollowing(user.profile(), user.isFollow(followee));
    }

    @Transactional
    public ProfileModel followUser(Long loginId, Username username) {
        User user = userService.findById(loginId);
        User followee = userService.findByUsername(username);
        user.followingUser(followee);
        return ProfileModel.fromProfileAndFollowing(user.profile(), user.isFollow(followee));
    }

    @Transactional
    public ProfileModel unfollowUser(Long loginId, Username username) {
        User user = userService.findById(loginId);
        User followee = userService.findByUsername(username);
        user.unfollowingUser(followee);
        return ProfileModel.fromProfileAndFollowing(user.profile(), user.isFollow(followee));
    }

}
