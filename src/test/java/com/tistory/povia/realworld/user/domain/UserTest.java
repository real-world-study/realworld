package com.tistory.povia.realworld.user.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    private String username;
    private String password;
    private Email email;

    @BeforeEach
    void setup() {
        username = "";
        password = "";
        email = new Email("test@test.com");
    }

    @Test
    @DisplayName("패스워드 빈칸 금지")
    void notNullTest() {
        var builder = User.builder().email(email).password(null).username(username);

        assertThatThrownBy(() -> builder.build());
    }

    @Test
    @DisplayName("중복된 회원은 가입하지 못함")
    void duplicateTest() {}
}
