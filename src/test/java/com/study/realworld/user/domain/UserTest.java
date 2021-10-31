package com.study.realworld.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
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

    private User user;
    private User followee;

    @BeforeEach
    void beforeEachTest() {
        user = User.Builder()
            .id(1L)
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .build();

        followee = User.Builder()
            .id(2L)
            .profile(Username.of("jakefriend"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jakefriend@jake.jake"))
            .password(Password.of("jakejake"))
            .build();
    }

    @Test
    void userTest() {
        User user = new User();
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
    @DisplayName("특정 유저가 following했던 유저라면 exception이 발생해야 한다.")
    void checkIsFollowingUserTest() {

        // given
        Set<User> userSet = new HashSet<>();
        userSet.add(followee);
        Followees followees = Followees.of(userSet);
        user = User.Builder()
            .id(1L)
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .followees(followees)
            .build();

        // when & then
        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> user.checkIsFollowingUser(followee))
            .withMessageMatching(ErrorCode.INVALID_FOLLOW.getMessage());
    }

    @Test
    @DisplayName("특정 유저가 following안한 유저라면 exception이 발생해야 한다.")
    void checkIsUnfollowingUserTest() {

        // given
        Set<User> userSet = new HashSet<>();
        Followees followees = Followees.of(userSet);
        user = User.Builder()
            .id(1L)
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .followees(followees)
            .build();

        // when & then
        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> user.checkIsUnfollowingUser(followee))
            .withMessageMatching(ErrorCode.INVALID_UNFOLLOW.getMessage());
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
