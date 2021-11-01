package com.study.realworld.follow.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import com.study.realworld.follow.domain.Follow;
import com.study.realworld.follow.service.model.response.FollowResponse;
import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Follows;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.UserService;
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
public class FollowServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private FollowService followService;

    private User user;
    private User followee;

    @BeforeEach
    void beforeEach() {
        user = User.Builder()
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .follows(Follows.of(new HashSet<>()))
            .build();

        followee = User.Builder()
            .profile(Username.of("jakefriend"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jakefriend@jake.jake"))
            .password(Password.of("jakejake"))
            .follows(Follows.of(new HashSet<>()))
            .build();
    }

    @Nested
    @DisplayName("followUser 유저 팔로우 기능 테스트")
    class followUserTest {

        private Long userId = 1L;
        private Username username = Username.of("jakefriend");

        @Test
        @DisplayName("login 유저를 찾을 수 없으면 exception이 발생해야 한다.")
        void followUserExceptionByNotFoundLoginUserTest() {

            // setup & given
            when(userService.findById(userId)).thenThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> followService.followUser(userId, username))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("follow할 유저를 찾을 수 없으면 exception이 발생해야 한다.")
        void followUserExceptionByNotFoundUserTest() {

            // setup & given
            when(userService.findById(userId)).thenReturn(user);
            when(userService.findByUsername(username)).thenThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> followService.followUser(userId, username))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("이미 팔로윙한 유저를 팔로윙하는 경우 exception이 발생해야 한다.")
        void followingUserExceptionByExistFollowTest() {

            // setup & given
            Set<Follow> followSet = new HashSet<>();
            Follow follow = Follow.builder()
                .follower(user)
                .followee(followee)
                .build();
            followSet.add(follow);
            user = User.Builder()
                .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
                .email(Email.of("jake@jake.jake"))
                .password(Password.of("jakejake"))
                .follows(Follows.of(followSet))
                .build();
            when(userService.findById(userId)).thenReturn(user);
            when(userService.findByUsername(username)).thenReturn(followee);

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> followService.followUser(userId, username))
                .withMessageMatching(ErrorCode.INVALID_FOLLOW.getMessage());
        }

        @Test
        @DisplayName("유저를 팔로우할 수 있다.")
        void followUserSuccessTest() {

            // setup & given
            when(userService.findById(userId)).thenReturn(user);
            when(userService.findByUsername(username)).thenReturn(followee);

            FollowResponse expected = FollowResponse.from(followee.profile(), true);

            // when
            FollowResponse result = followService.followUser(userId, username);

            // then
            System.out.println(result);
        }

    }

    @Nested
    @DisplayName("unfollowUser 유저 언팔로우 기능 테스트")
    class unfollowUserTest {

        private Long userId = 1L;
        private Username username = Username.of("jakefriend");

        @Test
        @DisplayName("login 유저를 찾을 수 없으면 exception이 발생해야 한다.")
        void unfollowUserExceptionByNotFoundLoginUserTest() {

            // setup & given
            when(userService.findById(userId)).thenThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> followService.followUser(userId, username))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("unfollow할 유저를 찾을 수 없으면 exception이 발생해야 한다.")
        void unfollowUserExceptionByNotFoundUserTest() {

            // setup & given
            when(userService.findById(userId)).thenReturn(user);
            when(userService.findByUsername(username)).thenThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> followService.followUser(userId, username))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("팔로윙 안한 유저를 언팔로윙하는 경우 exception이 발생해야 한다.")
        void unfollowingExceptionByNoExistFollowTest() {

            // setup & given
            when(userService.findById(userId)).thenReturn(user);
            when(userService.findByUsername(username)).thenReturn(followee);

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> followService.unfollowUser(userId, username))
                .withMessageMatching(ErrorCode.INVALID_UNFOLLOW.getMessage());
        }

        @Test
        @DisplayName("유저를 언팔로우할 수 있다.")
        void unfollowUserSuccessTest() {

            // setup & given
            Set<Follow> followSet = new HashSet<>();
            Follow follow = Follow.builder()
                .follower(user)
                .followee(followee)
                .build();
            followSet.add(follow);
            user = User.Builder()
                .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
                .email(Email.of("jake@jake.jake"))
                .password(Password.of("jakejake"))
                .follows(Follows.of(followSet))
                .build();
            when(userService.findById(userId)).thenReturn(user);
            when(userService.findByUsername(username)).thenReturn(followee);

            FollowResponse expected = FollowResponse.from(followee.profile(), false);

            // when
            FollowResponse result = followService.unfollowUser(userId, username);

            // then
            System.out.println(result);
        }

    }

}
