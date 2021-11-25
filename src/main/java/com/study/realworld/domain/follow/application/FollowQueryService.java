package com.study.realworld.domain.follow.application;

import com.study.realworld.domain.follow.domain.Follow;
import com.study.realworld.domain.follow.domain.FollowQueryDSLRepository;
import com.study.realworld.domain.follow.domain.FollowRepository;
import com.study.realworld.domain.follow.dto.ProfileResponse;
import com.study.realworld.domain.follow.error.exception.FollowNotFoundException;
import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.UserName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FollowQueryService {

    private final UserQueryService userQueryService;
    private final FollowQueryDSLRepository followQueryDSLRepository;
    private final FollowRepository followRepository;

    public ProfileResponse profile(final Long userId, final UserName username) {
        final User me = userQueryService.findById(userId);
        final User target = userQueryService.findByUserName(username);
        final boolean isFollow = existsByFolloweeAndFollower(target, me);
        return new ProfileResponse(target, isFollow);
    }

    public boolean existsByFolloweeAndFollower(final User followee, final User follower) {
        return followRepository.existsByFolloweeAndFollower(followee, follower);
    }

    public Follow findByFolloweeAndFollower(final User followee, final User follower) {
        return followRepository
                .findByFolloweeAndFollower(followee, follower)
                .orElseThrow(FollowNotFoundException::new);
    }

    public ProfileResponse profile2(final Long userId, final UserName username) {
        final User user = userQueryService.findById(userId);
        final User target = userQueryService.findByUserName(username);
        return followQueryDSLRepository.userInfoAndFollowable(target, user);
    }
}
