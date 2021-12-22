package com.study.realworld.domain.follow.application;

import com.study.realworld.domain.follow.domain.Follow;
import com.study.realworld.domain.follow.domain.FollowQueryRepository;
import com.study.realworld.domain.follow.dto.ProfileResponse;
import com.study.realworld.domain.follow.error.exception.FollowNotFoundException;
import com.study.realworld.domain.user.domain.persist.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FollowQueryService {

    private final FollowQueryRepository followQueryRepository;

    public boolean existsByFolloweeAndFollower(final User followee, final User follower) {
        return followQueryRepository.existsByFolloweeAndFollower(followee, follower);
    }

    public Follow findByFolloweeAndFollower(final User followee, final User follower) {
        return followQueryRepository
                .findByFolloweeAndFollower(followee, follower)
                .orElseThrow(FollowNotFoundException::new);
    }

    public ProfileResponse userInfoAndFollowable(final User followee, final User follower) {
        return followQueryRepository.userInfoAndFollowable(followee, follower);
    }
}
