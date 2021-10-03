package com.study.realworld.domain.follow.domain;

import com.study.realworld.domain.user.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static com.study.realworld.domain.follow.domain.FollowTest.followBuilder;
import static com.study.realworld.domain.user.domain.BioTest.BIO;
import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.UserTest.userBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class FollowRepositoryTest {

    @Autowired private TestEntityManager testEntityManager;
    @Autowired private FollowRepository followRepository;

    @DisplayName("FollowRepository 인스턴스 save() 테스트")
    @Test
    void save_test() {
        final User following = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        final User follower = userBuilder(new Email("Email2@email.com"), new Name("differentUserName"), new Password("Password2"), new Bio("Bio2"), new Image("Image2"));
        final Follow follow = followBuilder(following, follower);

        testEntityManager.persist(following);
        testEntityManager.persist(follower);
        final Follow savedFollow = followRepository.save(follow);

        assertAll(
                () -> assertThat(savedFollow).isNotNull(),
                () -> assertThat(savedFollow).isExactlyInstanceOf(Follow.class),
                () -> assertThat(savedFollow).isEqualTo(follow)
        );
    }

    @DisplayName("FollowRepository 인스턴스 findById() 테스트")
    @Test
    void findById_test() {
        final User following = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        final User follower = userBuilder(new Email("Email2@email.com"), new Name("differentUserName"), new Password("Password2"), new Bio("Bio2"), new Image("Image2"));
        final Follow follow = followBuilder(following, follower);

        testEntityManager.persist(following);
        testEntityManager.persist(follower);
        testEntityManager.persist(follow);

        final Follow findFollow = followRepository.findById(1L).get();

        assertAll(
                () -> assertThat(findFollow).isNotNull(),
                () -> assertThat(findFollow).isExactlyInstanceOf(Follow.class),
                () -> assertThat(findFollow).isEqualTo(follow)
        );
    }

    @DisplayName("FollowRepository 인스턴스 delete() 테스트")
    @Test
    void delete_test() {
        final User following = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        final User follower = userBuilder(new Email("Email2@email.com"), new Name("differentUserName"), new Password("Password2"), new Bio("Bio2"), new Image("Image2"));
        final Follow follow = followBuilder(following, follower);

        testEntityManager.persist(following);
        testEntityManager.persist(follower);
        testEntityManager.persist(follow);

        final Follow findFollow = followRepository.findById(1L).get();
        followRepository.delete(findFollow);

        assertThat(followRepository.count()).isEqualTo(0);
    }

}
