package com.study.realworld.domain.follow.dto;

import com.study.realworld.domain.user.domain.persist.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.user.domain.persist.UserTest.testUser;
import static com.study.realworld.domain.user.domain.vo.util.UserVOFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("언팔로우 결과(UnFollowResponse)")
class UnFollowResponseTest {

    @Test
    void 유저_정보를_통해_객체_생성이_가능하다() {
        final User user = testUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final UnFollowResponse unFollowResponse = UnFollowResponse.from(user);

        assertAll(
                () -> assertThat(unFollowResponse).isNotNull(),
                () -> assertThat(unFollowResponse).isExactlyInstanceOf(FollowResponse.class)
        );
    }

    @Test
    void 언팔로우_응답_정보를_반환할_수_있다() {
        final User user = testUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final UnFollowResponse unFollowResponse = UnFollowResponse.from(user);

        assertAll(
                () -> assertThat(unFollowResponse.userName()).isEqualTo(USER_NAME),
                () -> assertThat(unFollowResponse.userBio()).isEqualTo(USER_BIO),
                () -> assertThat(unFollowResponse.userImage()).isEqualTo(USER_IMAGE),
                () -> assertThat(unFollowResponse.isFollowing()).isEqualTo(false)
        );
    }
}
