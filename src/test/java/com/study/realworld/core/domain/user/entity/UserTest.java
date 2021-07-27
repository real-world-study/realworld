package com.study.realworld.core.domain.user.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * @author Jeongjoon Seo
 */
@ExtendWith(MockitoExtension.class)
class UserTest {

    @Test
    void UserEntityTest() {
        final LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                        .userName("name")
                        .email("email")
                        .password("password")
                        .bio(null)
                        .image(null)
                        .createdAt(now)
                        .build();

        assertNull(user.getId());
        assertSame("name", user.getUserName());
        assertSame("email", user.getEmail());
        assertSame("password", user.getPassword());
        assertNull(user.getBio());
        assertNull(user.getImage());
        assertSame(now, user.getCreatedAt());
        assertNull(user.getUpdatedAt());
        assertNull(user.getDeletedAt());
    }
}
