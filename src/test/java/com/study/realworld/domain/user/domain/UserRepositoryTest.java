package com.study.realworld.domain.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static com.study.realworld.domain.user.domain.UserTest.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("UserRepository 인스턴스 save() 테스트")
    @Test
    void save_test() {
        final User expected = USER;
        final User actual = userRepository.save(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("UserRepository 인스턴스 findById() 테스트")
    @Test
    void findById_test() {
        final User expected = testEntityManager.persist(USER);
        final User actual = userRepository.findById(1L).get();

        assertThat(actual).isEqualTo(expected);
    }

}