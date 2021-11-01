package com.study.realworld.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.study.realworld.follow.domain.Follow;
import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
            .followees(new Follows())
            .build();

        followee = User.Builder()
            .id(2L)
            .profile(Username.of("jakefriend"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jakefriend@jake.jake"))
            .password(Password.of("jakejake"))
            .followees(new Follows())
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

    @Nested
    @DisplayName("followUser user 팔로윙 기능")
    class followUserTest {

        @Test
        @DisplayName("이미 팔로윙한 유저를 팔로윙하는 경우 exception이 발생해야 한다.")
        void followUserExceptionByExistFollowTest() {

            // given
            Set<Follow> followSet = new HashSet<>();
            Follow follow = Follow.builder()
                .follower(user)
                .followee(followee)
                .build();
            followSet.add(follow);
            user = User.Builder()
                .id(1L)
                .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
                .email(Email.of("jake@jake.jake"))
                .password(Password.of("jakejake"))
                .followees(Follows.of(followSet))
                .build();

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> user.followUser(followee))
                .withMessageMatching(ErrorCode.INVALID_FOLLOW.getMessage());
        }

        @Test
        @DisplayName("정상적으로 유저를 팔로우할 수 있다.")
        void followUserSuccessTest() {

            // when
            boolean result = user.followUser(followee);

            // then
            assertTrue(result);
        }
    }

    @Nested
    @DisplayName("unfollowUser user 언팔로윙 기능")
    class unfollowUserTest {

        @Test
        @DisplayName("팔로윙 안한 유저를 언팔로윙하는 경우 exception이 발생해야 한다.")
        void unfollowUserExceptionByNoExistFollowTest() {

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> user.unfollowUser(followee))
                .withMessageMatching(ErrorCode.INVALID_UNFOLLOW.getMessage());
        }

        @Test
        @DisplayName("정상적으로 유저를 언팔로우할 수 있다.")
        void unfollowUserSuccessTest() {

            // given
            Set<Follow> followSet = new HashSet<>();
            Follow follow = Follow.builder()
                .follower(user)
                .followee(followee)
                .build();
            followSet.add(follow);
            user = User.Builder()
                .id(1L)
                .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
                .email(Email.of("jake@jake.jake"))
                .password(Password.of("jakejake"))
                .followees(Follows.of(followSet))
                .build();

            // when
            boolean result = user.unfollowUser(followee);

            // then
            assertFalse(result);
        }
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
