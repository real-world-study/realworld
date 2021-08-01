package com.tistory.povia.realworld.user.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

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
    }

    @Test
    @DisplayName("비밀번호는 null, blank 가 아니어야 한다.")
    void notNullTest() {
        var builder = User.builder().email(email).password(null).username(username);

        assertThatThrownBy(() -> builder.build()).isInstanceOf(IllegalArgumentException.class).hasMessage("password should be provided");
    }

    @Test
    @DisplayName("중복된 회원은 가입하지 못함")
    void duplicateTest() {}
}
