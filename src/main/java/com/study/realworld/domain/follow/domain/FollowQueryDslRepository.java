package com.study.realworld.domain.follow.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.realworld.domain.follow.dto.FollowableDto;
import com.study.realworld.domain.user.domain.persist.User;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static com.study.realworld.domain.follow.domain.QFollow.follow;

@Repository
public class FollowQueryDslRepository {
    private final JPAQueryFactory query;

    public FollowQueryDslRepository(final JPAQueryFactory jpaQueryFactory) {
        this.query = jpaQueryFactory;
    }
   
    public FollowableDto existMeAndFollowing(final User me, final User following) {
        final Integer count = query.selectOne()
                .from(follow)
                .where(follow.following.eq(following), follow.follower.eq(me))
                .fetchFirst();
        boolean followable = !Objects.isNull(count);
        return FollowableDto.fromUserAndFollowable(me, followable);
    }

}
