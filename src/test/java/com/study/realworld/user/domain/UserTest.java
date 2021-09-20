package com.study.realworld.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
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
        Username username = Username.of("username");
        Email email = Email.of("email@email.com");
        Password password = Password.of("password");
        Bio bio = Bio.of("bio");
        Image image = Image.of("image.jpg");

        // when
        User user = User.Builder()
            .id(id)
            .profile(username, bio, image)
            .email(email)
            .password(password)
            .build();

        // then
        assertThat(user.id()).isEqualTo(id);
        assertThat(user.username()).isEqualTo(username);
        assertThat(user.email()).isEqualTo(email);
        assertThat(user.password()).isEqualTo(password);
        assertThat(user.bio()).isEqualTo(bio);
        assertThat(user.image()).isEqualTo(image);
    }

    @Test
    @DisplayName("encodePassword 메소드가 실행되었을 때 User 객체의 비밀번호가 인코딩되어야 한다.")
    void userEncodePasswordTest() {

        // setup & given
        User user = User.Builder().password(Password.of("password")).build();
        when(passwordEncoder.encode(user.password().password()))
            .thenReturn("encoded_password");

        // when
        user.encodePassword(passwordEncoder);

        // then
        assertThat(user.password().password()).isEqualTo("encoded_password");
    }

    @Test
    @DisplayName("입력된 비밀번호가 존재하는 비밀번호와 같으면 Exception이 반환되지 않아야 한다.")
    void passwordMatchTest() {

        // setup
        when(passwordEncoder.matches("password", "encoded_password")).thenReturn(true);

        // given
        User user = User.Builder().password(Password.of("encoded_password")).build();
        Password password = Password.of("password");

        // when & then
        assertDoesNotThrow(() -> user.login(password, passwordEncoder));
    }

    @Test
    @DisplayName("입력된 비밀번호가 존재하는 비밀번호와 다르면 Excpetion을 반환해야 한다.")
    void passwordDismatchTest() {

        // setup
        when(passwordEncoder.matches("password", "encoded_password")).thenReturn(false);

        // given
        User user = User.Builder().password(Password.of("encoded_password")).build();
        Password password = Password.of("password");

        // when & then
        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> user.login(password, passwordEncoder))
            .withMessageMatching(ErrorCode.PASSWORD_DISMATCH.getMessage());
    }

    @Test
    @DisplayName("특정 유저를 follow하여 following set에 저장할 수 있다.")
    void followingTest() {

        // given
        User user = User.Builder()
            .profile(Username.of("username"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email@email.com"))
            .build();
        User followingUser = User.Builder()
            .profile(Username.of("followingUser"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email2@email2.com"))
            .build();

        // when
        user.followingUser(followingUser);

        // then
        assertThat(user).isNotNull();
    }

    // TODO : Exception 메시지 구체화
    @Test
    @DisplayName("이미 follow된 유저를 follow할 경우 Exception이 발생해야 한다.")
    void followingAlreadyExceptionTest() {

        // given
        User user = User.Builder()
            .profile(Username.of("username"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email@email.com"))
            .build();
        User followingUser = User.Builder()
            .profile(Username.of("followingUser"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email2@email2.com"))
            .build();
        user.followingUser(followingUser);

        // when & then
        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> user.followingUser(followingUser))
            .withMessageMatching(ErrorCode.INVALID_FOLLOW.getMessage());
    }

    @Test
    @DisplayName("특정 유저를 unfollow할 수 있다.")
    void unfollowingTest() {

        // given
        User user = User.Builder()
            .profile(Username.of("username"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email@email.com"))
            .build();
        User followingUser = User.Builder()
            .profile(Username.of("followingUser"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email2@email2.com"))
            .build();
        user.followingUser(followingUser);

        // when
        user.unfollowingUser(followingUser);

        // then
        assertThat(user).isNotNull();
    }

    // TODO : Exception 메시지 구체화
    @Test
    @DisplayName("follow 안된 유저를 unfollow할 경우 Exception이 발생해야 한다.")
    void unfollowingNotExceptionTest() {

        // given
        User user = User.Builder()
            .profile(Username.of("username"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email@email.com"))
            .build();
        User followingUser = User.Builder()
            .profile(Username.of("followingUser"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email2@email2.com"))
            .build();

        // when & then
        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> user.unfollowingUser(followingUser))
            .withMessageMatching(ErrorCode.INVALID_UNFOLLOW.getMessage());
    }

    @Test
    @DisplayName("follow 여부를 확인할 수 있다.")
    void isFollowTest() {

        // given
        User user = User.Builder()
            .profile(Username.of("username"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email@email.com"))
            .build();
        User followingUser = User.Builder()
            .profile(Username.of("followingUser"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email2@email2.com"))
            .build();
        user.followingUser(followingUser);

        // when
        boolean result = user.isFollow(followingUser);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void userEqualsHashCodeTest() {

        // given
        User user = User.Builder().email(Email.of("test@test.com")).build();
        User copyUser = User.Builder().email(Email.of("test@test.com")).build();

        // when & then
        assertThat(user)
            .isEqualTo(copyUser)
            .hasSameHashCodeAs(copyUser);
    }

}
