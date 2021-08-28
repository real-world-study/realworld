package com.study.realworld.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        User user = User.Builder()
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
        User input = User.Builder()
            .id(1L)
            .username(new Username("username"))
            .email(new Email("test@test.com"))
            .password(new Password("password"))
            .bio("bio")
            .image("image")
            .build();

        // when
        User user = User.Builder(input).build();

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
        User user = User.Builder().password(new Password("password")).build();
        when(passwordEncoder.encode(user.getPassword().getPassword()))
            .thenReturn("encoded_password");

        // when
        user.encodePassword(passwordEncoder);

        // then
        assertThat(user.getPassword().getPassword()).isEqualTo("encoded_password");
    }

    @Test
    @DisplayName("입력된 비밀번호가 존재하는 비밀번호와 같으면 Exception이 반환되지 않아야 한다.")
    void passwordMatchTest() {

        // setup
        when(passwordEncoder.matches("password", "encoded_password")).thenReturn(true);

        // given
        User user = User.Builder().password(new Password("encoded_password")).build();
        Password password = new Password("password");

        // when & then
        assertDoesNotThrow(() -> user.login(password, passwordEncoder));
    }

    @Test
    @DisplayName("입력된 비밀번호가 존재하는 비밀번호와 다르면 Excpetion을 반환해야 한다.")
    void passwordDismatchTest() {

        // setup
        when(passwordEncoder.matches("password", "encoded_password")).thenReturn(false);

        // given
        User user = User.Builder().password(new Password("encoded_password")).build();
        Password password = new Password("password");

        // when & then
        assertThatExceptionOfType(RuntimeException.class)
            .isThrownBy(() -> user.login(password, passwordEncoder))
            .withMessageMatching("password is different from old password.");
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void userEqualsHashCodeTest() {

        // given
        User user = User.Builder().email(new Email("test@test.com")).build();
        User copyUser = User.Builder().email(new Email("test@test.com")).build();

        // when & then
        assertThat(user)
            .isEqualTo(copyUser)
            .hasSameHashCodeAs(copyUser);
    }

}
