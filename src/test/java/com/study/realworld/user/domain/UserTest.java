package com.study.realworld.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void userTest() {
        User user = new User();
    }

    @Test
    void userBuilderTest() {

        // given
        Long id = 1L;
        Username username = new Username("username");
        Email email = new Email("email@email.com");
        Password password = new Password("password");
        String bio = "bio";
        String image = "image.jpg";

        // when
        User user = new User.Builder()
            .id(id)
            .username(username)
            .email(email)
            .password(password)
            .bio(bio)
            .image(image)
            .build();

        // then
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getBio()).isEqualTo(bio);
        assertThat(user.getImage()).isEqualTo(image);
    }

    @Test
    void userBuilderParamUserTest() {

        // given
        User input = new User.Builder()
            .id(1L)
            .username(new Username("username"))
            .email(new Email("email"))
            .password(new Password("password"))
            .bio("bio")
            .image("image")
            .build();

        // when
        User user = new User.Builder(input).build();

        // then
        assertThat(user.getId()).isEqualTo(input.getId());
        assertThat(user.getUsername()).isEqualTo(input.getUsername());
        assertThat(user.getEmail()).isEqualTo(input.getEmail());
        assertThat(user.getPassword()).isEqualTo(input.getPassword());
        assertThat(user.getBio()).isEqualTo(input.getBio());
        assertThat(user.getImage()).isEqualTo(input.getImage());
    }

    @Test
    @DisplayName("encodePassword 메소드가 실행되었을 때 User 객체의 비밀번호가 인코딩되어야 한다.")
    void userEncodePasswordTest() {

        // setup & given
        User user = new User.Builder().password(new Password("password")).build();
        when(passwordEncoder.encode(valueOf(user.getPassword()))).thenReturn("encoded_password");

        // when
        user.encodePassword(passwordEncoder);

        // then
        assertThat(user.getPassword().getPassword()).isEqualTo("encoded_password");
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void userEqualsHashCodeTest() {

        // given
        User user = new User.Builder().email(new Email("test@test.com")).build();
        User copyUser = new User.Builder().email(new Email("test@test.com")).build();

        // when & then
        assertThat(user)
            .isEqualTo(copyUser)
            .hasSameHashCodeAs(copyUser);
    }

}
