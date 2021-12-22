package com.study.realworld.domain.follow.application;

import com.study.realworld.domain.follow.domain.Follow;
import com.study.realworld.domain.follow.domain.FollowQueryRepository;
import com.study.realworld.domain.follow.dto.ProfileResponse;
import com.study.realworld.domain.follow.error.exception.FollowNotFoundException;
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

@DisplayName("팔로우 쿼리 서비스(FollowQueryService)")
@ExtendWith(MockitoExtension.class)
class FollowQueryServiceTest {

    @Mock
    private FollowQueryRepository followQueryRepository;

    @InjectMocks
    private FollowQueryService followQueryService;


    @Test
    void 팔로우_여부를_반환한다() {
        final User followee = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User follower = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);

        willReturn(false).given(followQueryRepository).existsByFolloweeAndFollower(any(), any());

        final boolean actual = followQueryService.existsByFolloweeAndFollower(followee, follower);
        assertThat(actual).isFalse();
    }

    @Test
    void 팔로우_정보를_찾는다() {
        final User followee = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User follower = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Follow follow = createFollow(follower, followee);

        willReturn(Optional.of(follow)).given(followQueryRepository).findByFolloweeAndFollower(any(), any());

        final Follow findFollow = followQueryService.findByFolloweeAndFollower(followee, follower);
        assertThat(follow).isEqualTo(findFollow);
    }

    @Test
    void 팔로우_정보가_없다면_예외를_발생시킨다() {
        final User followee = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User follower = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);

        willReturn(Optional.empty()).given(followQueryRepository).findByFolloweeAndFollower(any(), any());

        assertThatThrownBy(() -> followQueryService.findByFolloweeAndFollower(followee, follower))
                .isExactlyInstanceOf(FollowNotFoundException.class)
                .hasMessage("팔로우 정보를 찾을 수 없습니다");
    }

    @Test
    void 팔로우_정보를_찾는다_QueryDSL() {
        final User followee = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User follower = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final ProfileResponse profileResponse = new ProfileResponse(followee, false);

        willReturn(profileResponse).given(followQueryRepository).userInfoAndFollowable(any(), any());

        final ProfileResponse findProfileResponse = followQueryService.userInfoAndFollowable(followee, follower);
        assertAll(
                () -> assertThat(findProfileResponse.userName()).isEqualTo(followee.userName()),
                () -> assertThat(findProfileResponse.userBio()).isEqualTo(followee.userBio()),
                () -> assertThat(findProfileResponse.userImage()).isEqualTo(followee.userImage()),
                () -> assertThat(findProfileResponse.isFollowing()).isFalse()
        );
    }
}
