package com.study.realworld.domain.follow.application;

import com.study.realworld.domain.follow.domain.Follow;
import com.study.realworld.domain.follow.domain.FollowQueryDSLRepository;
import com.study.realworld.domain.follow.domain.FollowRepository;
import com.study.realworld.domain.follow.domain.FollowTest;
import com.study.realworld.domain.follow.dto.ProfileResponse;
import com.study.realworld.domain.follow.error.exception.FollowNotFoundException;
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

import java.util.Optional;

import static com.study.realworld.domain.user.domain.persist.UserTest.testUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;

@DisplayName("팔로우 쿼리 서비스(FollowQueryService)")
@ExtendWith(MockitoExtension.class)
class FollowQueryServiceTest {

    @Mock
    private UserQueryService userQueryService;

    @Mock
    private FollowQueryDSLRepository followQueryDSLRepository;

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private FollowQueryService followQueryService;

    @Test
    void 특정_유저_정보_및_팔로우_여부_조회() {
        final User me = testUser("user1@gmail.com", "user1", "password1", "bio1", "image1");
        final User target = testUser("user2@gmail.com", "user2", "password2", "bio2", "image2");
        willReturn(me).given(userQueryService).findById(any());
        willReturn(target).given(userQueryService).findByUserName(any());
        willReturn(false).given(followRepository).existsByFolloweeAndFollower(any(), any());

        final ProfileResponse profileResponse = followQueryService.profile(1L, UserName.from("user2"));
        assertAll(
                () -> assertThat(profileResponse.userName()).isEqualTo(UserName.from("user2")),
                () -> assertThat(profileResponse.userBio()).isEqualTo(UserBio.from("bio2")),
                () -> assertThat(profileResponse.userImage()).isEqualTo(UserImage.from("image2")),
                () -> assertThat(profileResponse.isFollowing()).isFalse()
        );
    }

    @Test
    void 팔로우_여부를_반환한다() {
        final User user1 = testUser("user1@gmail.com", "user1", "password1", "bio1", "image1");
        final User user2 = testUser("user2@gmail.com", "user2", "password2", "bio2", "image2");
        willReturn(false).given(followRepository).existsByFolloweeAndFollower(any(), any());

        final boolean actual = followQueryService.existsByFolloweeAndFollower(user1, user2);
        assertThat(actual).isFalse();
    }

    @Test
    void 팔로우_정보를_찾는다() {
        final User followee = testUser("user1@gmail.com", "user1", "password1", "bio1", "image1");
        final User follower = testUser("user2@gmail.com", "user2", "password2", "bio2", "image2");
        final Follow follow = FollowTest.testFollow(follower, followee);
        willReturn(Optional.of(follow)).given(followRepository).findByFolloweeAndFollower(any(), any());

        final Follow findFollow = followQueryService.findByFolloweeAndFollower(followee, follower);
        assertThat(follow).isEqualTo(findFollow);
    }

    @Test
    void 팔로우_정보가_없다면_예외를_발생시킨다() {
        final User followee = testUser("user1@gmail.com", "user1", "password1", "bio1", "image1");
        final User follower = testUser("user2@gmail.com", "user2", "password2", "bio2", "image2");
        willReturn(Optional.empty()).given(followRepository).findByFolloweeAndFollower(any(), any());

        assertThatThrownBy(() -> followQueryService.findByFolloweeAndFollower(followee, follower))
                .isExactlyInstanceOf(FollowNotFoundException.class)
                .hasMessage("팔로우 정보를 찾을 수 없습니다");
    }

    @Test
    void QueryDSL_로_특정_유저_정보_및_팔로우_여부_조회() {
        final User me = testUser("user1@gmail.com", "user1", "password1", "bio1", "image1");
        final User target = testUser("user2@gmail.com", "user2", "password2", "bio2", "image2");
        final ProfileResponse profileResponse = new ProfileResponse(target, false);
        willReturn(me).given(userQueryService).findById(any());
        willReturn(target).given(userQueryService).findByUserName(any());
        willReturn(profileResponse).given(followQueryDSLRepository).userInfoAndFollowable(any(), any());

        final ProfileResponse findProfileResponse = followQueryService.profile2(1L, UserName.from("user2"));
        assertAll(
                () -> assertThat(findProfileResponse.userName()).isEqualTo(UserName.from("user2")),
                () -> assertThat(findProfileResponse.userBio()).isEqualTo(UserBio.from("bio2")),
                () -> assertThat(findProfileResponse.userImage()).isEqualTo(UserImage.from("image2")),
                () -> assertThat(findProfileResponse.isFollowing()).isFalse()
        );
    }
}
