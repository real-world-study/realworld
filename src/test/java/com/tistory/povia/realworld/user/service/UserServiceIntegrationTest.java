package com.tistory.povia.realworld.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tistory.povia.realworld.user.domain.Email;
import com.tistory.povia.realworld.user.domain.User;
import com.tistory.povia.realworld.user.exception.DuplicatedEmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceIntegrationTest {

    @Autowired private UserService userService;

    private String username;

    private Email email;

    private String password;

    @BeforeEach
    void setUp() {
        username = "tester";
        email = new Email("test@test.com");
        password = "test";
    }

    @Test
    @DisplayName("일반적인 방법으로는 join에 성공한 후 결과를 return해야 함")
    void joinSuccessTest() {
        Email email = new Email("test2@test.com");

        User user =
                userService.join(
                        User.builder().username("test").email(email).password(password).build());

        assertThat(user).isNotNull();
        assertThat(user.id()).isNotNull();
        assertThat(user.email()).isEqualTo(email);
        assertThat(user.createdAt()).isNotNull();
        assertThat(user.updatedAt()).isNotNull();
        assertThat(user.deletedAt()).isNull();
    }

    @Test
    @DisplayName("중복된 이메일로는 가입 불가능")
    void joinFailedTest() {
        User oldUser = User.builder().username(username).email(email).password(password).build();
        userService.join(oldUser);

        User newUser = User.builder().username("new user").email(email).password("test2").build();

        assertThatThrownBy(() -> userService.join(newUser))
                .isInstanceOf(DuplicatedEmailException.class)
                .hasMessage("Duplicated Email found");
    }
}
