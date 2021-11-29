package com.study.realworld.domain.follow.application;

import com.study.realworld.domain.follow.dto.ProfileResponse;
import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.UserName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FollowUserQueryService {

    private final UserQueryService userQueryService;
    private final FollowQueryService followQueryService;

    public ProfileResponse profile(final Long userId, final UserName username) {
        final User user = userQueryService.findById(userId);
        final User target = userQueryService.findByUserName(username);
        final boolean isFollow = followQueryService.existsByFolloweeAndFollower(target, user);
        return new ProfileResponse(target, isFollow);
    }

    public ProfileResponse profile2(final Long userId, final UserName username) {
        final User user = userQueryService.findById(userId);
        final User target = userQueryService.findByUserName(username);
        return followQueryService.userInfoAndFollowable(target, user);
    }
}
