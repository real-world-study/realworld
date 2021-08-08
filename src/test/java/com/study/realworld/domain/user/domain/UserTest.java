package com.study.realworld.domain.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserTest {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String BIO = "bio";
    public static final String IMAGE = "image";

    @DisplayName("User 인스턴스 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final User user = new User();

        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user).isExactlyInstanceOf(User.class)
        );
    }

    @DisplayName("User 인스턴스 빌더 테스트")
    @Test
    void builder_test() {
        final User user = userBuilder(new Email(EMAIL), USERNAME, PASSWORD, BIO, IMAGE);

        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user).isExactlyInstanceOf(User.class)
        );
    }

    @DisplayName("User 인스턴스 getter() 테스트")
    @Test
    void getter_test() {
        final User user = userBuilder(new Email(EMAIL), USERNAME, PASSWORD, BIO, IMAGE);

        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user).isExactlyInstanceOf(User.class),
                () -> assertThat(user.email().email()).isEqualTo(EMAIL),
                () -> assertThat(user.username()).isEqualTo(USERNAME),
                () -> assertThat(user.bio()).isEqualTo(BIO),
                () -> assertThat(user.image()).isEqualTo(IMAGE)
        );
    }

    @DisplayName("User 인스턴스 checkPassword() 테스트")
    @Test
    void checkPassword_test() {
        final String invalidPassword = "INVALID_PASSWORD";
        final User user = userBuilder(new Email(EMAIL), USERNAME, PASSWORD, BIO, IMAGE);

        assertAll(
                () -> assertThat(user.checkPassword(invalidPassword)).isFalse(),
                () -> assertThat(user.checkPassword(PASSWORD)).isTrue()
        );
    }

    public static final User userBuilder(final Email email, final String username,
                                          final String password, final String bio, final String image) {
        return User.Builder()
                .email(email)
                .username(username)
                .password(password)
                .bio(bio)
                .image(image)
                .build();
    }

}