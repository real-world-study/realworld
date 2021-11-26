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

import static com.study.realworld.domain.user.domain.persist.UserTest.testUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("팔로우 QueryDsl 저장소 테스트(FollowQueryDSLRepository)")
@DataJpaTest
class FollowQueryDSLRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    private FollowQueryDSLRepository followQueryDSLRepository;

    @BeforeEach
    void setUp() {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        followQueryDSLRepository = new FollowQueryDSLRepository(queryFactory);
    }

    @Test
    void userInfoAndFollowable() {
        final User follower = testUser("user1@gmail.com", "user1", "password1", "bio1", "image1");
        final User followee = testUser("user2@gmail.com", "user2", "password2", "bio2", "image2");
        entityManager.persist(follower);
        entityManager.persist(followee);

        final Follow follow = Follow.builder()
                .follower(follower)
                .followee(followee)
                .build();
        entityManager.persist(follow);

        final ProfileResponse profileResponse = followQueryDSLRepository.userInfoAndFollowable(followee, follower);
        assertAll(
                () -> assertThat(profileResponse.userName()).isEqualTo(followee.userName()),
                () -> assertThat(profileResponse.userBio()).isEqualTo(followee.userBio()),
                () -> assertThat(profileResponse.userImage()).isEqualTo(followee.userImage()),
                () -> assertThat(profileResponse.isFollowing()).isEqualTo(true)
        );
    }

}