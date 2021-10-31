package com.study.realworld.follow.domain;

import com.study.realworld.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerAndFollowee(User follower, User followee);

}
