package com.study.realworld.domain.follow.application;

import com.study.realworld.domain.follow.domain.Follow;
import com.study.realworld.domain.follow.domain.FollowQueryDslRepository;
import com.study.realworld.domain.follow.domain.FollowRepository;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.persist.UserRepository;
import com.study.realworld.domain.user.error.exception.IdentityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final FollowQueryDslRepository followQueryDslRepository;

    public FollowService(final UserRepository userRepository, final FollowRepository followRepository, final FollowQueryDslRepository followQueryDslRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.followQueryDslRepository = followQueryDslRepository;
    }

    public Follow following(final Long myId, final Long targetId) {
        final User me = findUserById(myId);
        final User target = findUserById(targetId);
        final Follow follow = follow(target, me);
        me.addFollowing(follow);
        return followRepository.save(follow);
    }

    public User unfollowing(final Long myId, final Long targetId) {
        final User me = findUserById(myId);
        final User target = findUserById(targetId);
        final Follow follow = followRepository.findByFollowingAndFollower(target, me).orElseThrow(IllegalArgumentException::new);
        followRepository.delete(follow);
        return me;
    }

    private Follow follow(final User following, final User follower) {
        return Follow.Builder()
                .following(following)
                .follower(follower)
                .build();
    }

    private User findUserById(final Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IdentityNotFoundException(id));
    }


}
