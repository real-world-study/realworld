package com.study.realworld.domain.follow.application;

import com.study.realworld.domain.follow.dto.ProfileResponse;
import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.UserBio;
import com.study.realworld.domain.user.domain.vo.UserImage;
import com.study.realworld.domain.user.domain.vo.UserName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.study.realworld.domain.user.domain.persist.UserTest.testUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
        final User me = testUser("user1@gmail.com", "user1", "password1", "bio1", "image1");
        final User target = testUser("user2@gmail.com", "user2", "password2", "bio2", "image2");
        willReturn(me).given(userQueryService).findById(any());
        willReturn(target).given(userQueryService).findByUserName(any());
        willReturn(false).given(followQueryService).existsByFolloweeAndFollower(any(), any());

        final ProfileResponse profileResponse = followUserQueryService.profile(1L, UserName.from("user2"));
        assertAll(
                () -> assertThat(profileResponse.userName()).isEqualTo(UserName.from("user2")),
                () -> assertThat(profileResponse.userBio()).isEqualTo(UserBio.from("bio2")),
                () -> assertThat(profileResponse.userImage()).isEqualTo(UserImage.from("image2")),
                () -> assertThat(profileResponse.isFollowing()).isFalse()
        );
    }

    @Test
    void QueryDSL_로_특정_유저_정보_및_팔로우_여부_조회() {
        final User me = testUser("user1@gmail.com", "user1", "password1", "bio1", "image1");
        final User target = testUser("user2@gmail.com", "user2", "password2", "bio2", "image2");
        final ProfileResponse profileResponse = new ProfileResponse(target, false);
        willReturn(me).given(userQueryService).findById(any());
        willReturn(target).given(userQueryService).findByUserName(any());
        willReturn(profileResponse).given(followQueryService).userInfoAndFollowable(any(), any());

        final ProfileResponse findProfileResponse = followUserQueryService.profile2(1L, UserName.from("user2"));
        assertAll(
                () -> assertThat(findProfileResponse.userName()).isEqualTo(UserName.from("user2")),
                () -> assertThat(findProfileResponse.userBio()).isEqualTo(UserBio.from("bio2")),
                () -> assertThat(findProfileResponse.userImage()).isEqualTo(UserImage.from("image2")),
                () -> assertThat(findProfileResponse.isFollowing()).isFalse()
        );
    }
}
