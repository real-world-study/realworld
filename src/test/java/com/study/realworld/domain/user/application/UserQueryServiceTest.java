package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.persist.UserQueryRepository;
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
    private UserQueryRepository userQueryRepository;

    @InjectMocks
    private UserQueryService userQueryService;

    @Test
    void 아이덴티티로_유저를_찾는다() {
        final User entity = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        ReflectionTestUtils.setField(entity, "userId", 1L);
        willReturn(Optional.of(entity)).given(userQueryRepository).findById(any());

        final User user = userQueryService.findById(entity.userId());
        assertThat(user).isEqualTo(entity);
    }

    @Test
    void 아이덴티티가_다르면_유저를_찾을_수_없다() {
        final User entity = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        ReflectionTestUtils.setField(entity, "userId", 1L);
        willReturn(Optional.empty()).given(userQueryRepository).findById(any());

        assertThatThrownBy(() -> userQueryService.findById(2L))
                .isExactlyInstanceOf(IdentityNotFoundException.class)
                .hasMessage(String.format("식별자 : [ %s ] 를 찾을 수 없습니다.", 2L));
    }

    @Test
    void 이메일로_유저를_찾는다() {
        final User entity = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        willReturn(Optional.of(entity)).given(userQueryRepository).findByUserEmail(any());

        final User user = userQueryService.findByMemberEmail(entity.userEmail());
        assertThat(user).isEqualTo(entity);
    }

    @Test
    void 부적절한_이메일로는_유저를_찾을_수_없다() {
        willReturn(Optional.empty()).given(userQueryRepository).findByUserEmail(any());

        assertThatThrownBy(() -> userQueryService.findByMemberEmail(INVALID_USER_EMAIL))
                .isExactlyInstanceOf(EmailNotFoundException.class)
                .hasMessage(String.format("이메일 : [ %s ] 를 찾을 수 없습니다.", INVALID_USER_EMAIL.userEmail()));
    }
}
