package com.study.realworld.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.model.ProfileModel;
import java.util.Optional;
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
    private UserRepository userRepository;

    @InjectMocks
    private ProfileService profileService;

    private User loginUser;
    private User followUser;

    @BeforeEach
    void beforeEach() {
        loginUser = User.Builder()
            .id(1L)
            .profile(Username.of("username"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email@email.com"))
            .build();
        followUser = User.Builder()
            .id(2L)
            .profile(Username.of("followuser"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email2@email2.com"))
            .build();
    }

    @Nested
    @DisplayName("Follow user method")
    class followingUser {

        @Test
        @DisplayName("현재 유저의 id가 존재하지 않는 유저라면 Exception이 발생해야 한다.")
        void loginUserNotFoundTest() {

            // setup & given
            Long loginId = 1L;
            Username followingUsername = Username.of("followuser");
            when(userRepository.findById(loginId)).thenReturn(Optional.empty());

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> profileService.followUser(loginId, followingUsername))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("현재 검색하고자하는 username가 존재하지 않는 유저라면 Exception이 발생해야 한다.")
        void followUsernameNotFoundTest() {

            // setup & given
            Long loginId = 1L;
            Username followingUsername = Username.of("followuser");
            when(userRepository.findById(loginId)).thenReturn(Optional.of(loginUser));
            when(userRepository.findByProfileUsername(followingUsername)).thenReturn(Optional.empty());

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> profileService.followUser(loginId, followingUsername))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("follow한 결과를 반환할 수 있다.")
        void followSuccessTest() {

            // setup & given
            Long loginId = 1L;
            Username followingUsername = Username.of("followuser");
            when(userRepository.findById(loginId)).thenReturn(Optional.of(loginUser));
            when(userRepository.findByProfileUsername(followingUsername)).thenReturn(Optional.of(followUser));

            // when
            ProfileModel result = profileService.followUser(loginId, followingUsername);

            // then
            assertThat(result.getProfile()).isEqualTo(loginUser.profile());
            assertTrue(result.isFollow());
        }

    }

    @Nested
    @DisplayName("Unfollow user method")
    class unfollowingUser {

        @Test
        @DisplayName("현재 유저의 id가 존재하지 않는 유저라면 Exception이 발생해야 한다.")
        void loginUserNotFoundTest() {

            // setup & given
            Long loginId = 1L;
            Username followingUsername = Username.of("followuser");
            when(userRepository.findById(loginId)).thenReturn(Optional.empty());

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> profileService.unfollowUser(loginId, followingUsername))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("현재 검색하고자하는 username가 존재하지 않는 유저라면 Exception이 발생해야 한다.")
        void followUsernameNotFoundTest() {

            // setup & given
            Long loginId = 1L;
            Username followingUsername = Username.of("followuser");
            when(userRepository.findById(loginId)).thenReturn(Optional.of(loginUser));
            when(userRepository.findByProfileUsername(followingUsername)).thenReturn(Optional.empty());

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> profileService.unfollowUser(loginId, followingUsername))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("unfollow한 결과를 반환할 수 있다.")
        void unfollowSuccessTest() {

            // setup & given
            Long loginId = 1L;
            Username followingUsername = Username.of("followuser");
            when(userRepository.findById(loginId)).thenReturn(Optional.of(loginUser));
            when(userRepository.findByProfileUsername(followingUsername)).thenReturn(Optional.of(followUser));
            loginUser.followingUser(followUser);

            // when
            ProfileModel result = profileService.unfollowUser(loginId, followingUsername);

            // then
            assertThat(result.getProfile()).isEqualTo(loginUser.profile());
            assertFalse(result.isFollow());
        }

    }

    @Test
    @DisplayName("특정 유저의 profile 정보를 반환할 수 있다.")
    void findProfileTest() {

        // setup & given
        Long loginId = 1L;
        Username followingUsername = Username.of("followuser");
        when(userRepository.findById(loginId)).thenReturn(Optional.of(loginUser));
        when(userRepository.findByProfileUsername(followingUsername)).thenReturn(Optional.of(followUser));
        ProfileModel expected = ProfileModel.fromProfileAndFollowing(loginUser.profile(), false);

        // when
        ProfileModel result = profileService.findProfile(loginId, followingUsername);

        // then
        assertThat(result.getProfile()).isEqualTo(expected.getProfile());
        assertThat(result.isFollow()).isEqualTo(expected.isFollow());
    }

}