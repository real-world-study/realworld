package com.study.realworld.domain.follow.domain;

import com.study.realworld.domain.user.domain.persist.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static com.study.realworld.domain.follow.util.FollowFixture.createFollow;
import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("팔로우 저장소(FollowRepository)")
@DataJpaTest
class FollowRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private FollowRepository followRepository;

    @AfterEach
    void afterEach() {
        testEntityManager.clear();
    }

    @Test
    void 필로우를_했을시_팔로우_여부를_반환한다() {
        final User followee = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User follower = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);

        testEntityManager.persist(follower);
        testEntityManager.persist(followee);
        testEntityManager.persist(createFollow(followee, follower));

        assertThat(followRepository.existsByFolloweeAndFollower(followee, follower)).isTrue();
    }

    @Test
    void 필로우를_안했을시_팔로우_여부를_반환한다() {
        final User followee = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User follower = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);

        testEntityManager.persist(follower);
        testEntityManager.persist(followee);

        assertThat(followRepository.existsByFolloweeAndFollower(followee, follower)).isFalse();
    }

    @Test
    void 팔로우_존재시_객체를_반환한다() {
        final User followee = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User follower = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);

        testEntityManager.persist(follower);
        testEntityManager.persist(followee);
        testEntityManager.persist(createFollow(followee, follower));

        final Follow follow = followRepository.findByFolloweeAndFollower(followee, follower).get();
        assertAll(
                () -> assertThat(follow).isNotNull(),
                () -> assertThat(follow.followee()).isEqualTo(followee),
                () -> assertThat(follow.follower()).isEqualTo(follower)
        );
    }

    @Test
    void 팔로우_존재하지_않는다면_객체를_반환할_수_없다() {
        final User followee = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User follower = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);

        testEntityManager.persist(follower);
        testEntityManager.persist(followee);

        final boolean actual = followRepository.findByFolloweeAndFollower(followee, follower).isPresent();
        assertThat(actual).isFalse();
    }
}
