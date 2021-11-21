package com.study.realworld.acceptance;


import com.study.realworld.domain.auth.dto.Login;
import com.study.realworld.domain.follow.dto.ProfileResponse;
import com.study.realworld.domain.user.dto.UserJoin;
import com.study.realworld.global.common.AccessToken;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("팔로우 관련 인수 테스트")
class FollowAcceptanceTest extends AcceptanceTest {

    private UserJoin.Response user1;
    private UserJoin.Response user2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        final String user1String = "woozi@naver.com";
        user1 = 회원_가입_되어있음(user1String);

        final String user2String = "ori@naver.com";
        user2 = 회원_가입_되어있음(user2String);
    }

    @Test
    void 특정_사람의_프로필을_조회한다() {
        final Login.Response loginResponse = 로그인_되어있음(user1.userEmail().value());
        final ExtractableResponse<Response> response = 프로필_조회_요청(loginResponse.accessToken(), user2.userName().value());
        final ProfileResponse profileResponse = response.as(ProfileResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(profileResponse.userName()).isEqualTo(user2.userName()),
                () -> assertThat(profileResponse.userBio()).isEqualTo(user2.userBio()),
                () -> assertThat(profileResponse.userImage()).isEqualTo(user2.userImage()),
                () -> assertThat(profileResponse.isFollowing()).isFalse()
        );
    }

    protected ExtractableResponse<Response> 프로필_조회_요청(final AccessToken accessToken, final String userName) {
        return RestAssured.given()
                .header(AUTHORIZATION, BEARER + accessToken.accessToken())
                .when()
                .get(String.format("/api/profiles/%s", userName))
                .then()
                .extract();
    }
}
