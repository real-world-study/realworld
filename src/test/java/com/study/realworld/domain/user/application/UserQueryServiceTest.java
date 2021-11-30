package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.persist.UserRepository;
import com.study.realworld.domain.user.error.exception.EmailNotFoundException;
import com.study.realworld.domain.user.error.exception.IdentityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;

@DisplayName("유저 쿼리 서비스(UserQueryService)")
@ExtendWith({MockitoExtension.class})
class UserQueryServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserQueryService userQueryService;

    @Test
    void 아이덴티티로_유저를_찾는다() {
        final User entity = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        ReflectionTestUtils.setField(entity, "userId", 1L);
        willReturn(Optional.of(entity)).given(userRepository).findById(any());

        final User user = userQueryService.findById(entity.userId());
        assertThat(user).isEqualTo(entity);
    }

    @Test
    void 아이덴티티가_다르면_유저를_찾을_수_없다() {
        final User entity = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        ReflectionTestUtils.setField(entity, "userId", 1L);
        willReturn(Optional.empty()).given(userRepository).findById(any());

        assertThatThrownBy(() -> userQueryService.findById(2L))
                .isExactlyInstanceOf(IdentityNotFoundException.class)
                .hasMessage(String.format("식별자 : [ %s ] 를 찾을 수 없습니다.", 2L));
    }

    @Test
    void 이메일로_유저를_찾는다() {
        final User entity = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        willReturn(Optional.of(entity)).given(userRepository).findByUserEmail(any());

        final User user = userQueryService.findByMemberEmail(entity.userEmail());
        assertThat(user).isEqualTo(entity);
    }

    @Test
    void 유저가_존재한다면_이메일로_유저_존재_여부를_얻는다() {
        willReturn(true).given(userRepository).existsByUserEmail(any());

        final boolean actual = userQueryService.existsByUserEmail(USER_EMAIL);
        assertThat(actual).isTrue();
    }

    @Test
    void 유저가_존재한다면_이메일로_유저_존재_없음을_얻는다() {
        willReturn(false).given(userRepository).existsByUserEmail(any());

        final boolean actual = userQueryService.existsByUserEmail(USER_EMAIL);
        assertThat(actual).isFalse();
    }

    @Test
    void 부적절한_이메일로는_유저를_찾을_수_없다() {
        willReturn(Optional.empty()).given(userRepository).findByUserEmail(any());

        assertThatThrownBy(() -> userQueryService.findByMemberEmail(INVALID_USER_EMAIL))
                .isExactlyInstanceOf(EmailNotFoundException.class)
                .hasMessage(String.format("이메일 : [ %s ] 를 찾을 수 없습니다.", INVALID_USER_EMAIL.userEmail()));
    }
}
