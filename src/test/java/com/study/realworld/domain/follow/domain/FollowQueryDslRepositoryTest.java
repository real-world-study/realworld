package com.study.realworld.domain.follow.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.realworld.domain.follow.dto.FollowableDto;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static com.study.realworld.domain.user.domain.persist.UserTest.userBuilder;
import static com.study.realworld.domain.user.domain.vo.BioTest.BIO;
import static com.study.realworld.domain.user.domain.vo.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.vo.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.vo.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.vo.PasswordTest.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FollowQueryDslRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    private JPAQueryFactory jpaQueryFactory;

    @BeforeEach
    void setUp() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @DisplayName("FollowQueryDslRepositoryTest 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final FollowQueryDslRepository followQueryDslRepository = new FollowQueryDslRepository(jpaQueryFactory);

        assertAll(
                () -> assertThat(followQueryDslRepository).isNotNull(),
                () -> assertThat(followQueryDslRepository).isExactlyInstanceOf(FollowQueryDslRepository.class)
        );
    }

    @DisplayName("FollowQueryDslRepositoryTest 인스턴스 existMeAndFollowing() 테스트")
    @Test
    void existMeAndFollowing_test() {
        // given
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        final User other = userBuilder(new Email("email2"), new Name("username2"), new Password("password2"), new Bio("bio2"), new Image("image2"));
        entityManager.persist(user);
        entityManager.persist(other);

        // when
        final FollowQueryDslRepository followQueryDslRepository = new FollowQueryDslRepository(jpaQueryFactory);
        final FollowableDto followableDto = followQueryDslRepository.existMeAndFollowing(user, other);

        assertAll(
                () -> assertThat(followableDto).isNotNull(),
                () -> assertThat(followableDto).isExactlyInstanceOf(FollowableDto.class)
        );
    }



}