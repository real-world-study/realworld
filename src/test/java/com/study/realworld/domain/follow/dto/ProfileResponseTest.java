package com.study.realworld.domain.follow.dto;

import com.study.realworld.domain.user.domain.persist.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.study.realworld.domain.user.domain.persist.UserTest.testUser;
import static com.study.realworld.domain.user.domain.vo.util.UserVOFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("유저 정보(Profile Response)")
class ProfileResponseTest {

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(booleans = {true, false})
    void 유저_정보와_팔로우_여부를_통해_객체_생성이_가능하다(final boolean followable) {
        final User user = testUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final ProfileResponse profileResponse = new ProfileResponse(user, followable);

        assertAll(
                () -> assertThat(profileResponse).isNotNull(),
                () -> assertThat(profileResponse).isExactlyInstanceOf(FollowResponse.class)
        );
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(booleans = {true, false})
    void 프로필_응답_정보를_반환할_수_있다(final boolean followable) {
        final User user = testUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final ProfileResponse profileResponse = new ProfileResponse(user, followable);

        assertAll(
                () -> assertThat(profileResponse.userName()).isEqualTo(USER_NAME),
                () -> assertThat(profileResponse.userBio()).isEqualTo(USER_BIO),
                () -> assertThat(profileResponse.userImage()).isEqualTo(USER_IMAGE),
                () -> assertThat(profileResponse.isFollowing()).isEqualTo(followable)
        );
    }
}
