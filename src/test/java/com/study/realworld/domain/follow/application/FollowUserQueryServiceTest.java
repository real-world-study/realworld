package com.study.realworld.domain.follow.application;

import com.study.realworld.domain.follow.dto.ProfileResponse;
import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;

@DisplayName("팔로우 유저 쿼리 서비스(FollowUserQueryService)")
@ExtendWith(MockitoExtension.class)
class FollowUserQueryServiceTest {

    @Mock
    private FollowQueryService followQueryService;

    @Mock
    private UserQueryService userQueryService;

    @InjectMocks
    private FollowUserQueryService followUserQueryService;

    @Test
    void 특정_유저_정보_및_팔로우_여부_조회() {
        final User followee = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User follower = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);

        willReturn(followee).given(userQueryService).findByUserName(any());
        willReturn(follower).given(userQueryService).findById(any());
        willReturn(false).given(followQueryService).existsByFolloweeAndFollower(any(), any());

        final ProfileResponse profileResponse = followUserQueryService.profile(1L, followee.userName());
        assertAll(
                () -> assertThat(profileResponse.userName()).isEqualTo(followee.userName()),
                () -> assertThat(profileResponse.userBio()).isEqualTo(followee.userBio()),
                () -> assertThat(profileResponse.userImage()).isEqualTo(followee.userImage()),
                () -> assertThat(profileResponse.isFollowing()).isFalse()
        );
    }

    @Test
    void QueryDSL_로_특정_유저_정보_및_팔로우_여부_조회() {
        final User followee = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User follower = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final ProfileResponse profileResponse = new ProfileResponse(followee, false);

        willReturn(follower).given(userQueryService).findById(any());
        willReturn(followee).given(userQueryService).findByUserName(any());
        willReturn(profileResponse).given(followQueryService).userInfoAndFollowable(any(), any());

        final ProfileResponse findProfileResponse = followUserQueryService.profile2(1L, followee.userName());
        assertAll(
                () -> assertThat(findProfileResponse.userName()).isEqualTo(followee.userName()),
                () -> assertThat(findProfileResponse.userBio()).isEqualTo(followee.userBio()),
                () -> assertThat(findProfileResponse.userImage()).isEqualTo(followee.userImage()),
                () -> assertThat(findProfileResponse.isFollowing()).isFalse()
        );
    }
}
