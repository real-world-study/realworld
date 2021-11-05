package com.study.realworld.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import com.study.realworld.follow.domain.Follow;
import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Follows;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.dto.response.ProfileResponse;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private ProfileService profileService;

    private User user;
    private User followee;

    @BeforeEach
    void beforeEach() {
        user = User.Builder()
            .id(1L)
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .follows(Follows.of(new HashSet<>()))
            .build();

        followee = User.Builder()
            .id(2L)
            .profile(Username.of("jakefriend"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jakefriend@jake.jake"))
            .password(Password.of("jakejake"))
            .build();
    }

    @Nested
    @DisplayName("findProfile profile 조회 (parameter = username")
    class findProfileByUsernameTest {

        @Test
        @DisplayName("username에 해당하는 유저가 없다면 exception이 발생해야 한다.")
        void findProfileExceptionByNotFoundUserTest() {

            // setup & given
            Username username = Username.of("username");
            when(userService.findByUsername(username)).thenThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

            // when & then
            assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> profileService.findProfile(username))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("username을 받아 해당하는 유저의 프로필을 반환할 수 있다.")
        void findProfileSuccessTest() {

            // setup & given
            Username username = followee.username();
            when(userService.findByUsername(username)).thenReturn(followee);

            ProfileResponse expected = ProfileResponse.fromUserAndFollowing(followee, false);

            // when
            ProfileResponse result = profileService.findProfile(username);

            // then
            assertThat(result).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("findProfile profile 조회 (parameter = loginId, username")
    class findProfileByLoginIdAndUsernameTest {

        @Test
        @DisplayName("loginId에 해당하는 유저가 없다면 exception이 발생해야 한다.")
        void findProfileExceptionByNotFoundLoginIdTest() {

            // setup & given
            Long userId = user.id();
            Username username = followee.username();
            when(userService.findById(userId)).thenThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> profileService.findProfile(userId, username))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("username에 해당하는 유저가 없다면 exception이 발생해야 한다.")
        void findProfileExceptionByNotFoundUsernameTest() {

            // setup & given
            Long userId = user.id();
            Username username = followee.username();
            when(userService.findById(userId)).thenReturn(user);
            when(userService.findByUsername(username)).thenThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> profileService.findProfile(userId, username))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("이미 팔로우한 유저라면 following이 true인 프로필이 반환되어야 한다.")
        void findProfileByTrueFollowingTest() {

            // setup & given
            Set<Follow> followset = new HashSet<>();
            Follow follow = Follow.builder()
                .follower(user)
                .followee(followee)
                .build();
            followset.add(follow);
            user = User.Builder()
                .id(1L)
                .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
                .email(Email.of("jake@jake.jake"))
                .password(Password.of("jakejake"))
                .follows(Follows.of(followset))
                .build();
            Long userId = user.id();
            Username username = followee.username();
            when(userService.findById(userId)).thenReturn(user);
            when(userService.findByUsername(username)).thenReturn(followee);

            ProfileResponse expected = ProfileResponse.fromUserAndFollowing(followee, true);

            // when
            ProfileResponse result = profileService.findProfile(userId, username);

            // then
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("팔로우안한 유저라면 following이 false인 프로필이 반환되어야 한다.")
        void findProfileByFalseFollowingTest() {

            // setup & given
            Long userId = user.id();
            Username username = followee.username();
            when(userService.findById(userId)).thenReturn(user);
            when(userService.findByUsername(username)).thenReturn(followee);

            ProfileResponse expected = ProfileResponse.fromUserAndFollowing(followee, false);

            // when
            ProfileResponse result = profileService.findProfile(userId, username);

            // then
            assertThat(result).isEqualTo(expected);
        }

    }

}