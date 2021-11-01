package com.study.realworld.follow.service;

import com.study.realworld.follow.service.model.response.FollowResponse;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowService {

    private final UserService userService;

    public FollowService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public FollowResponse followUser(Long userId, Username username) {
        User user = userService.findById(userId);
        User followee = userService.findByUsername(username);

        return FollowResponse.from(followee.profile(), user.followUser(followee));
    }

    @Transactional
    public FollowResponse unfollowUser(Long userId, Username username) {
        User user = userService.findById(userId);
        User followee = userService.findByUsername(username);

        return FollowResponse.from(followee.profile(), user.unfollowUser(followee));
    }

}
