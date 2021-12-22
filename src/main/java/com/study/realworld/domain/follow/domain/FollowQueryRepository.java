package com.study.realworld.domain.follow.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.realworld.domain.follow.dto.ProfileResponse;
import com.study.realworld.domain.user.domain.persist.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

import static com.querydsl.core.types.ExpressionUtils.isNotNull;
import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.jpa.JPAExpressions.selectOne;
import static com.study.realworld.domain.follow.domain.QFollow.follow;
import static com.study.realworld.domain.user.domain.persist.QUser.user;

@RequiredArgsConstructor
@Repository
public class FollowQueryRepository {

    private final JPAQueryFactory query;

    public boolean existsByFolloweeAndFollower(final User followee, final User follower) {
        return Objects.nonNull(findFollow(followee, follower));
    }

    public Optional<Follow> findByFolloweeAndFollower(final User followee, final User follower) {
        return Optional.ofNullable(findFollow(followee, follower));
    }

    private Follow findFollow(final User followee, final User follower) {
        return query.selectFrom(follow)
                .where(follow.followee.eq(followee), follow.follower.eq(follower))
                .fetchOne();
    }

    public ProfileResponse userInfoAndFollowable(final User followee, final User follower) {
        return query.select(constructor(ProfileResponse.class, user,
                isNotNull(selectOne()
                        .from(follow)
                        .where(follow.followee.eq(followee), follow.follower.eq(follower))
                )
        ))
                .from(user)
                .where(user.eq(followee))
                .fetchFirst();
    }
}
