package com.study.realworld.domain.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.UserTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class UserRepositoryTest {

    @Autowired private TestEntityManager testEntityManager;
    @Autowired private UserRepository userRepository;

    @DisplayName("UserRepository 인스턴스 save() 테스트")
    @Test
    void save_test() {
        final User expected = userBuilder(new Email(EMAIL), new Name(USERNAME), PASSWORD, BIO, IMAGE);
        final User actual = userRepository.save(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("UserRepository 인스턴스 findById() 테스트")
    @Test
    void findById_test() {
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), PASSWORD, BIO, IMAGE);
        final User expected = testEntityManager.persist(user);
        final User actual = userRepository.findById(1L).get();

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("UserRepository 인스턴스 findAll() 테스트")
    @Test
    void findAll_test() {
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), PASSWORD, BIO, IMAGE);
        final User expected = testEntityManager.persist(user);
        final List<User> users = (List<User>) userRepository.findAll();

        assertAll(
                () -> assertThat(users.size()).isEqualTo(1),
                () -> assertThat(users.get(0)).isEqualTo(expected)
        );
    }

    @DisplayName("UserRepository 인스턴스 delete() 테스트")
    @Test
    void delete_test() {
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), PASSWORD, BIO, IMAGE);
        testEntityManager.persist(user);
        userRepository.delete(user);

        assertThat(userRepository.count()).isEqualTo(0);
    }


}