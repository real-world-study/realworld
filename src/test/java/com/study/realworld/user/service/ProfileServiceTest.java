package com.study.realworld.user.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import com.study.realworld.user.domain.Username;
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

            // setup & when
            Long loginId = 1L;
            Username followingUsername = Username.of("followuser");
            when(userRepository.findById(loginId)).thenReturn(Optional.of(loginUser));
            when(userRepository.findByUsername(followingUsername)).thenReturn(Optional.empty());

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> profileService.followUser(loginId, followingUsername))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }
    }

}