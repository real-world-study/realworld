package com.study.realworld.domain.user.domain.persist;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.EntityManager;

import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("유저 쿼리 레포지토리 테스트(UserQueryRepository)")
@DataJpaTest
class UserQueryRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private EntityManager entityManager;

    private UserQueryRepository userQueryRepository;

    @BeforeEach
    void setUp() {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        userQueryRepository = new UserQueryRepository(queryFactory);
    }

    @Test
    void 식별자를_통해_유저를_찾는다() {
        final User entity = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final User user = testEntityManager.persist(entity);
        final User findUser = userQueryRepository.findById(user.userId()).get();

        assertThat(user).isEqualTo(findUser);
    }

    @Test
    void 이메일을_통해_유저를_찾는다() {
        final User entity = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final User user = testEntityManager.persist(entity);
        final User findUser = userQueryRepository.findByUserEmail(user.userEmail()).get();

        assertThat(user).isEqualTo(findUser);
    }

    @Test
    void 이름을_통해_유저를_찾는다() {
        final User entity = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final User user = testEntityManager.persist(entity);
        final User findUser = userQueryRepository.findByUserName(user.userName()).get();

        assertThat(user).isEqualTo(findUser);
    }
}
