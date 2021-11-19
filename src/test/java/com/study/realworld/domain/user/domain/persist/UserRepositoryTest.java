package com.study.realworld.domain.user.domain.persist;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static com.study.realworld.domain.user.domain.persist.UserTest.testUser;
import static com.study.realworld.domain.user.domain.vo.util.UserVOFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void afterEach() {
        testEntityManager.clear();
    }

    @Test
    void 이메일로_유저를_찾을_수_있다() {
        final User entity = testEntityManager.persist(testUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE));
        final User user = userRepository.findByUserEmail(entity.userEmail()).get();

        assertThat(entity).isEqualTo(user);
    }

    @Test
    void 이메일로_유저_존재_여부를_반환할_수_있다() {
        final User entity = testEntityManager.persist(testUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE));
        final boolean actual = userRepository.existsByUserEmail(entity.userEmail());

        assertThat(actual).isTrue();
    }
}