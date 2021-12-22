package com.study.realworld.domain.follow.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.realworld.domain.follow.dto.ProfileResponse;
import com.study.realworld.domain.user.domain.persist.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static com.study.realworld.domain.follow.util.FollowFixture.createFollow;
import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("팔로우 QueryDsl 저장소 테스트(FollowQueryDSLRepository)")
@DataJpaTest
class FollowQueryRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    private FollowQueryRepository followQueryRepository;

    @BeforeEach
    void setUp() {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        followQueryRepository = new FollowQueryRepository(queryFactory);
    }

    @Test
    void userInfoAndFollowable() {
        final User followee = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User follower = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Follow follow = createFollow(followee, follower);

        entityManager.persist(followee);
        entityManager.persist(follower);
        entityManager.persist(follow);

        final ProfileResponse profileResponse = followQueryRepository.userInfoAndFollowable(followee, follower);
        assertAll(
                () -> assertThat(profileResponse.userName()).isEqualTo(followee.userName()),
                () -> assertThat(profileResponse.userBio()).isEqualTo(followee.userBio()),
                () -> assertThat(profileResponse.userImage()).isEqualTo(followee.userImage()),
                () -> assertThat(profileResponse.isFollowing()).isEqualTo(true)
        );
    }
}
