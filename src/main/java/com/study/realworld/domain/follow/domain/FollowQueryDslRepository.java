package com.study.realworld.domain.follow.domain;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.realworld.domain.follow.dto.FollowableDto;
import com.study.realworld.domain.user.domain.persist.User;
import org.springframework.stereotype.Repository;

import static com.study.realworld.domain.follow.domain.QFollow.follow;
import static com.study.realworld.domain.user.domain.persist.QUser.user;

@Repository
public class FollowQueryDslRepository {
    private final JPAQueryFactory query;

    public FollowQueryDslRepository(final JPAQueryFactory jpaQueryFactory) {
        this.query = jpaQueryFactory;
    }

    public FollowableDto existMeAndFollowing(final User me, final User following) {
        return query.select(Projections.constructor(FollowableDto.class, user,
                ExpressionUtils.isNotNull(JPAExpressions.selectOne()
                        .from(follow)
                        .where(follow.following.eq(following), follow.follower.eq(me)))))
                .from(user)
                .where(user.eq(me))
                .fetchFirst();
    }

}
