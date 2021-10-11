package com.study.realworld.domain.follow.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FollowQueryDslRepositoryTest {

    @Mock
    private JPAQueryFactory jpaQueryFactory;

    @DisplayName("FollowQueryDslRepositoryTest 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final FollowQueryDslRepository followQueryDslRepository = new FollowQueryDslRepository(jpaQueryFactory);
        assertAll(
                () -> assertThat(followQueryDslRepository).isNotNull(),
                () -> assertThat(followQueryDslRepository).isExactlyInstanceOf(FollowQueryDslRepository.class)
        );
    }

}