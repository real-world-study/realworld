package com.study.realworld.domain.follow.domain;

import com.study.realworld.domain.user.domain.persist.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowingAndFollower(User following, User follower);
}
