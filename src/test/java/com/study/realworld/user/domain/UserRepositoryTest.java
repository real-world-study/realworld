package com.study.realworld.user.domain;

import com.study.realworld.config.JpaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JpaConfig.class
))
class UserRepositoryTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String BIO = "bio";
    private static final String IMAGE = "image";

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {

        // given
        final User user = User.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        // when
        User result = userRepository.save(user);

        // then
        assertAll(
                () -> assertEquals(user.getEmail(), result.getEmail()),
                () -> assertEquals(user.getUsername(), result.getUsername()),
                () -> assertTrue(result.getCreatedAt() != null),
                () -> assertTrue(result.getUpdatedAt() != null),
                () -> assertTrue(result.getDeletedAt() == null)
        );
    }

}
