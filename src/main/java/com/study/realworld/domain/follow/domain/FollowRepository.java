package com.study.realworld.domain.follow.domain;

import com.study.realworld.domain.user.domain.persist.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @EntityGraph(attributePaths = {"followee", "follower"})
    Optional<Follow> findByFolloweeAndFollower(final User followee, final User follower);

    boolean existsByFolloweeAndFollower(final User followee, final User follower);
}
