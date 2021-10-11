package com.study.realworld.domain.follow.application;

import com.study.realworld.domain.follow.domain.FollowQueryDslRepository;
import com.study.realworld.domain.follow.domain.FollowRepository;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final FollowQueryDslRepository followQueryDslRepository;

    public FollowService(final FollowRepository followRepository, final FollowQueryDslRepository followQueryDslRepository) {
        this.followRepository = followRepository;
        this.followQueryDslRepository = followQueryDslRepository;
    }

}
