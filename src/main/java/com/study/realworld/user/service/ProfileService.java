package com.study.realworld.user.service;

import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.dto.response.ProfileResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final UserService userService;

    public ProfileService(UserService userService) {
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public ProfileResponse findProfile(Username username) {
        return ProfileResponse.fromUserAndFollowing(findUser(username), false);
    }

    @Transactional(readOnly = true)
    public ProfileResponse findProfile(Long loginId, Username username) {
        User user = findUser(loginId);
        User followee = findUser(username);

        return ProfileResponse.fromUserAndFollowing(followee, user.isFollow(followee));
    }

    private User findUser(Long id) {
        return userService.findById(id);
    }

    private User findUser(Username username) {
        return userService.findByUsername(username);
    }

}
