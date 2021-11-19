package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.persist.UserRepository;
import com.study.realworld.domain.user.domain.vo.util.TestPasswordEncoder;
import com.study.realworld.domain.user.dto.UserUpdate;
import com.study.realworld.domain.user.error.exception.DuplicatedEmailException;
import com.study.realworld.domain.user.error.exception.IdentityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static com.study.realworld.domain.user.domain.persist.UserTest.testUser;
import static com.study.realworld.domain.user.domain.vo.util.UserVOFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;

@ExtendWith(MockitoExtension.class)
class UserCommandServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserCommandService userCommandService;

    @BeforeEach
    void setUp() {
        userCommandService = new UserCommandService(userRepository, TestPasswordEncoder.initialize());
    }

    @Test
    void 이미_존재하는_엔티티가_없다면_회원가입에_성공한다() {
        final User user = testUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        ReflectionTestUtils.setField(user, "userId", 1L);
        willReturn(false).given(userRepository).existsByUserEmail(any());
        willReturn(user).given(userRepository).save(any());

        final User joinedUser = userCommandService.join(user);
        assertThat(joinedUser).isEqualTo(user);
    }

    @Test
    void 이미_존재하는_엔티티가_있다면_회원가입에_싪패한다() {
        final User user = testUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        ReflectionTestUtils.setField(user, "userId", 1L);
        willReturn(true).given(userRepository).existsByUserEmail(any());

        assertThatThrownBy(() -> userCommandService.join(user))
                .isExactlyInstanceOf(DuplicatedEmailException.class)
                .hasMessage(String.format("이메일 : [ %s ] 가 이미 존재합니다.", user.userEmail().value()));
    }

    @Test
    void 식별자가_올바르다면_엔티티의_값을_변경할_수_있다() {
        final User user = testUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        ReflectionTestUtils.setField(user, "userId", 1L);
        willReturn(Optional.of(user)).given(userRepository).findById(any());

        final UserUpdate.Request request = UserUpdate.Request.of(CHANGED_USER_EMAIL, CHANGED_USER_BIO, CHANGED_USER_IMAGE);
        final User updatedUser = userCommandService.update(user.userId(), request);

        assertAll(
                () -> assertThat(updatedUser.userEmail()).isEqualTo(CHANGED_USER_EMAIL),
                () -> assertThat(updatedUser.userBio()).isEqualTo(CHANGED_USER_BIO),
                () -> assertThat(updatedUser.userImage()).isEqualTo(CHANGED_USER_IMAGE)
        );
    }

    @Test
    void 식별자가_올바르지_않다면_엔티티의_값을_변경할_수_없다() {
        final User user = testUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        ReflectionTestUtils.setField(user, "userId", 1L);
        willReturn(Optional.empty()).given(userRepository).findById(any());

        final UserUpdate.Request request = UserUpdate.Request.of(CHANGED_USER_EMAIL, CHANGED_USER_BIO, CHANGED_USER_IMAGE);
        assertThatThrownBy(() -> userCommandService.update(2L, request))
                .isExactlyInstanceOf(IdentityNotFoundException.class)
                .hasMessage(String.format("식별자 : [ %s ] 를 찾을 수 없습니다.", 2L));
    }
}