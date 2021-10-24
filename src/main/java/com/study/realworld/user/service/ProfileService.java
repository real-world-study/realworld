package com.study.realworld.user.service;

import com.study.realworld.user.domain.Profile;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final UserService userService;

    public ProfileService(UserService userService) {
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public Profile findProfile(Long loginId, Username username) {
        User user = userService.findById(loginId);
        User followee = userService.findByUsername(username);

        return user.profileByFollowee(followee);
    }

    @Transactional
    public Profile followUser(Long loginId, Username username) {
        User user = userService.findById(loginId);
        User followee = userService.findByUsername(username);
        user.followingUser(followee);

        return user.profileByFollowee(followee);
    }

    @Transactional
    public Profile unfollowUser(Long loginId, Username username) {
        User user = userService.findById(loginId);
        User followee = userService.findByUsername(username);
        user.unfollowingUser(followee);

        return user.profileByFollowee(followee);
    }

}
