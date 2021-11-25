package com.study.realworld.domain.follow.application;

import com.study.realworld.domain.follow.domain.Follow;
import com.study.realworld.domain.follow.domain.FollowRepository;
import com.study.realworld.domain.follow.dto.FollowResponse;
import com.study.realworld.domain.follow.dto.UnFollowResponse;
import com.study.realworld.domain.follow.error.exception.DuplicatedFollowException;
import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.UserName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class FollowCommandService {

    private final UserQueryService userQueryService;
    private final FollowQueryService followQueryService;
    private final FollowRepository followRepository;

    public FollowResponse follow(final Long userId, final UserName userName) {
        final User followee = userQueryService.findByUserName(userName);
        final User follower = userQueryService.findById(userId);
        validateDuplicatedFollow(followee, follower);
        final Follow follow = followBuilder(followee, follower);
        return FollowResponse.from(followRepository.save(follow).followee());
    }

    public UnFollowResponse unfollow(final Long suerId, final UserName userName) {
        final User followee = userQueryService.findByUserName(userName);
        final User follower = userQueryService.findById(suerId);
        final Follow follow = followQueryService.findByFolloweeAndFollower(followee, follower);
        followRepository.delete(follow);
        return UnFollowResponse.from(followee);
    }

    private void validateDuplicatedFollow(final User followee, final User follower) {
        if (followQueryService.existsByFolloweeAndFollower(followee, follower)) {
            throw new DuplicatedFollowException();
        }
    }

    private Follow followBuilder(final User followee, final User follower) {
        return Follow.builder()
                .followee(followee)
                .follower(follower)
                .build();
    }
}
