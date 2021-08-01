package com.tistory.povia.realworld.user.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class UserTest {

    public static Stream<String> blankStrings() {
        return Stream.of(null, "", " ", "    ");
    }

    private String username;
    private String password;
    private Email email;
    private String bio;
    private String image;

    @BeforeEach
    void setup() {
        username = "test";
        password = "testpassword";
        email = new Email("test@test.com");
        bio = "test";
        image = "https://test.com/test.jpg";
    }

    @ParameterizedTest
    @MethodSource("blankStrings")
    @DisplayName("비밀번호는 null, blank 가 아니어야 한다.")
    void passwordNotNullTest(String password) {
        var builder = User.builder().email(email).password(password).username(username);

        assertThatThrownBy(() -> builder.build()).isInstanceOf(IllegalArgumentException.class).hasMessage("password should be provided");
    }

    @ParameterizedTest
    @MethodSource("blankStrings")
    @DisplayName("사용자 이름은 null, blank 가 아니어야 한다.")
    void usernameNotNullTest(String username) {
        var builder = User.builder().email(email).password(password).username(username);

        assertThatThrownBy(() -> builder.build()).isInstanceOf(IllegalArgumentException.class).hasMessage("username should be provided");
    }

    @Test
    @DisplayName("정상 생성 테스트 1 - 전체 데이터 존재할 경우")
    void successAllFieldsTest(){
        User user = User.builder().email(email).password(password).username(username).bio(bio).image(image).build();

        Assertions.assertAll(
          () -> user.email().equals(email),
          () -> user.password().equals(password),
          () -> user.username().equals(username),
          () -> user.bio().equals(bio),
          () -> user.image().equals(image)
        );
    }

    @Test
    @DisplayName("정상 생성 테스트 1 - 필수 데이터 존재할 경우")
    void successRequiredFieldsTest(){
        User user = User.builder().email(email).password(password).username(username).build();

        Assertions.assertAll(
          () -> user.email().equals(email),
          () -> user.password().equals(password),
          () -> user.username().equals(username)
        );
    }

}
