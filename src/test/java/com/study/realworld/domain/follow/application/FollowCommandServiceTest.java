package com.study.realworld.domain.follow.application;

import com.study.realworld.domain.follow.domain.Follow;
import com.study.realworld.domain.follow.domain.FollowRepository;
import com.study.realworld.domain.follow.dto.FollowResponse;
import com.study.realworld.domain.follow.dto.UnFollowResponse;
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

import static com.study.realworld.domain.follow.domain.FollowTest.testFollower;
import static com.study.realworld.domain.user.domain.persist.UserTest.testUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;

@DisplayName("팔로우 커멘드 서비스(FollowCommandService)")
@ExtendWith(MockitoExtension.class)
class FollowCommandServiceTest {

    @Mock
    private UserQueryService userQueryService;

    @Mock
    private FollowQueryService followQueryService;

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private FollowCommandService followCommandService;

    @Test
    void 팔로워_아이덴티티와_팔로위_이름을_입력하면_팔로우_할_수_있다() {
        final User follower = testUser("user1@gmail.com", "user1", "password1", "bio1", "image1");
        final User followee = testUser("user2@gmail.com", "user2", "password2", "bio2", "image2");
        final Follow follow = testFollower(followee, follower);
        willReturn(follower).given(userQueryService).findById(any());
        willReturn(followee).given(userQueryService).findByUserName(any());
        willReturn(false).given(followQueryService).existsByFolloweeAndFollower(any(), any());
        willReturn(follow).given(followRepository).save(any());

        final FollowResponse followResponse = followCommandService.follow(1L, UserName.from("user2"));
        assertAll(
                () -> assertThat(followResponse.userName()).isEqualTo(UserName.from("user2")),
                () -> assertThat(followResponse.userBio()).isEqualTo(UserBio.from("bio2")),
                () -> assertThat(followResponse.userImage()).isEqualTo(UserImage.from("image2")),
                () -> assertThat(followResponse.isFollowing()).isTrue()
        );
    }

    @Test
    void 팔로워_아이덴티티와_팔로위_이름을_입력하면_언팔로우_할_수_있다() {
        final User follower = testUser("user1@gmail.com", "user1", "password1", "bio1", "image1");
        final User followee = testUser("user2@gmail.com", "user2", "password2", "bio2", "image2");
        final Follow follow = testFollower(followee, follower);
        willReturn(follower).given(userQueryService).findById(any());
        willReturn(followee).given(userQueryService).findByUserName(any());
        willReturn(follow).given(followQueryService).findByFolloweeAndFollower(any(), any());

        final UnFollowResponse unFollowResponse = followCommandService.unfollow(1L, UserName.from("user2"));
        assertAll(
                () -> assertThat(unFollowResponse.userName()).isEqualTo(UserName.from("user2")),
                () -> assertThat(unFollowResponse.userBio()).isEqualTo(UserBio.from("bio2")),
                () -> assertThat(unFollowResponse.userImage()).isEqualTo(UserImage.from("image2")),
                () -> assertThat(unFollowResponse.isFollowing()).isFalse()
        );
    }
}
