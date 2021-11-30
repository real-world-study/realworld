package com.study.realworld.domain.follow.application;

import com.study.realworld.domain.follow.domain.Follow;
import com.study.realworld.domain.follow.domain.FollowRepository;
import com.study.realworld.domain.follow.dto.FollowResponse;
import com.study.realworld.domain.follow.dto.UnFollowResponse;
import com.study.realworld.domain.follow.error.exception.DuplicatedFollowException;
import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.study.realworld.domain.follow.util.FollowFixture.createFollow;
import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;

@DisplayName("팔로우 커멘드 서비스(FollowCommandService)")
@ExtendWith(MockitoExtension.class)
class FollowCommandServiceTest {

    @Mock
    private UserQueryService userQueryService;

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private FollowCommandService followCommandService;

    @Test
    void 팔로워_아이덴티티와_팔로위_이름을_입력하면_팔로우_할_수_있다() {
        final User followee = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User follower = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Follow follow = createFollow(followee, follower);

        willReturn(follower).given(userQueryService).findById(any());
        willReturn(followee).given(userQueryService).findByUserName(any());
        willReturn(false).given(followRepository).existsByFolloweeAndFollower(any(), any());
        willReturn(follow).given(followRepository).save(any());

        final FollowResponse followResponse = followCommandService.follow(1L, followee.userName());
        assertAll(
                () -> assertThat(followResponse.userName()).isEqualTo(followee.userName()),
                () -> assertThat(followResponse.userBio()).isEqualTo(followee.userBio()),
                () -> assertThat(followResponse.userImage()).isEqualTo(followee.userImage()),
                () -> assertThat(followResponse.isFollowing()).isTrue()
        );
    }

    @Test
    void 이미_팔로우_정보가_있다면_팔로우_할_수_없다() {
        final User followee = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User follower = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);

        willReturn(follower).given(userQueryService).findById(any());
        willReturn(followee).given(userQueryService).findByUserName(any());
        willReturn(true).given(followRepository).existsByFolloweeAndFollower(any(), any());

        assertThatThrownBy(() -> followCommandService.follow(1L, followee.userName()))
                .isExactlyInstanceOf(DuplicatedFollowException.class)
                .hasMessage("팔로우 정보가 이미 있습니다");
    }

    @Test
    void 팔로워_아이덴티티와_팔로위_이름을_입력하면_언팔로우_할_수_있다() {
        final User followee = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User follower = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Follow follow = createFollow(followee, follower);

        willReturn(follower).given(userQueryService).findById(any());
        willReturn(followee).given(userQueryService).findByUserName(any());
        willReturn(Optional.of(follow)).given(followRepository).findByFolloweeAndFollower(any(), any());

        final UnFollowResponse unFollowResponse = followCommandService.unfollow(1L, followee.userName());
        assertAll(
                () -> assertThat(unFollowResponse.userName()).isEqualTo(followee.userName()),
                () -> assertThat(unFollowResponse.userBio()).isEqualTo(followee.userBio()),
                () -> assertThat(unFollowResponse.userImage()).isEqualTo(followee.userImage()),
                () -> assertThat(unFollowResponse.isFollowing()).isFalse()
        );
    }
}
