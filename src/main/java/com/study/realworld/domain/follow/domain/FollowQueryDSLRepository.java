package com.study.realworld.domain.follow.domain;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.realworld.domain.follow.dto.ProfileResponse;
import com.study.realworld.domain.user.domain.persist.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.study.realworld.domain.follow.domain.QFollow.follow;
import static com.study.realworld.domain.user.domain.persist.QUser.user;

@RequiredArgsConstructor
@Repository
public class FollowQueryDSLRepository {

    private final JPAQueryFactory query;

    public ProfileResponse userInfoAndFollowable(final User followee, final User follower) {
        return query.select(Projections.constructor(ProfileResponse.class, user,
                ExpressionUtils.isNotNull(
                        JPAExpressions.selectOne()
                                .from(follow)
                                .where(follow.followee.eq(followee), follow.follower.eq(follower))
                )
        ))
                .from(user)
                .where(user.eq(followee))
                .fetchFirst();
    }
}
