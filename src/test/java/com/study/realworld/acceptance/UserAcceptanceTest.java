package com.study.realworld.acceptance;

import com.study.realworld.domain.auth.dto.Login;
import com.study.realworld.domain.user.domain.vo.UserEmail;
import com.study.realworld.domain.user.domain.vo.UserName;
import com.study.realworld.domain.user.dto.UserInfo;
import com.study.realworld.domain.user.dto.UserJoin;
import com.study.realworld.domain.user.dto.UserUpdate;
import com.study.realworld.domain.user.error.UserErrorResponse;
import com.study.realworld.global.common.AccessToken;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.study.realworld.domain.user.domain.vo.util.UserVOFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("User 관련 인수 테스트")
class UserAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원가입_성공() {
        final String userEmail = "kwj1270@gmail.com";
        final ExtractableResponse<Response> response = 회원_가입_요청(userEmail);
        final UserJoin.Response userJoinResponse = response.as(UserJoin.Response.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(userJoinResponse.userEmail().userEmail()).isEqualTo(userEmail)
        );
    }

    @Test
    void 회원가입_실패_이미_존재하는_이메일_경우() {
        final String userEmail = "kwj1270@gmail.com";

        회원_가입_되어있음(userEmail);
        final ExtractableResponse<Response> response = 회원_가입_요청(userEmail);
        final UserErrorResponse errorResponse = 유저_예외_결과(response);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.body()).contains("Email is Duplication")
        );
    }

    @Test
    void 유저_정보_얻기_성공() {
        final String userEmail = "kwj1270@gmail.com";
        회원_가입_되어있음(userEmail);
        final Login.Response loginResponse = 로그인_되어있음(userEmail);
        final ExtractableResponse<Response> response = 유저_정보_요청(loginResponse.accessToken());
        final UserInfo userInfo = response.as(UserInfo.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(userInfo.userEmail().userEmail()).isEqualTo(userEmail),
                () -> assertThat(userInfo.accessToken()).isNotNull(),
                () -> assertThat(userInfo.userName()).isEqualTo(UserName.from(userEmail.split("@")[0])),
                () -> assertThat(userInfo.userBio()).isNull(),
                () -> assertThat(userInfo.userImage()).isNull()
        );
    }

    @Test
    void 유저_정보_변경_성공() {
        final String userEmail = "kwj1270@gmail.com";
        final UserJoin.Response joinResponse = 회원_가입_되어있음(userEmail);
        final ExtractableResponse<Response> response = 유저_정보_변경_요청(joinResponse.accessToken());
        final UserUpdate.Response userUpdateResponse = response.as(UserUpdate.Response.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(userUpdateResponse.userEmail()).isEqualTo(OTHER_USER_EMAIL),
                () -> assertThat(userUpdateResponse.userBio()).isEqualTo(OTHER_USER_BIO),
                () -> assertThat(userUpdateResponse.userImage()).isEqualTo(OTHER_USER_IMAGE)
        );
    }

    @Test
    void 유저_정보_변경_실패() {
        final String userEmail = "kwj1270@gmail.com";
        final String otherEmail = "other@gmail.com";

        회원_가입_되어있음(userEmail);
        final UserJoin.Response joinResponse = 회원_가입_되어있음(otherEmail);

        final ExtractableResponse<Response> response = 비정상적인_유저_정보_변경_요청(userEmail, joinResponse.accessToken());
        final UserErrorResponse errorResponse = 유저_예외_결과(response);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.body()).contains("Email is Duplication")
        );
    }

    @Test
    void 유저_정보_삭제_성공() {
        final String userEmail = "kwj1270@gmail.com";
        final UserJoin.Response joinResponse = 회원_가입_되어있음(userEmail);
        final ExtractableResponse<Response> response = 유저_정보_삭제_요청(joinResponse.accessToken());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    protected ExtractableResponse<Response> 유저_정보_요청(final AccessToken accessToken) {
        return RestAssured.given()
                .header(AUTHORIZATION, BEARER + accessToken.accessToken())
                .when()
                .get("/api/user")
                .then()
                .extract();
    }

    protected ExtractableResponse<Response> 유저_정보_삭제_요청(final AccessToken accessToken) {
        return RestAssured.given()
                .header(AUTHORIZATION, BEARER + accessToken.accessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/users")
                .then()
                .extract();
    }

    protected ExtractableResponse<Response> 유저_정보_변경_요청(final AccessToken accessToken) {
        final UserUpdate.Request request = 정상적인_회원_변경_정보();
        return RestAssured.given()
                .header(AUTHORIZATION, BEARER + accessToken.accessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .put("/api/users")
                .then()
                .extract();
    }

    protected ExtractableResponse<Response> 비정상적인_유저_정보_변경_요청(final String userEmail, final AccessToken accessToken) {
        final UserUpdate.Request request = 비정상적인_회원_변경_정보(userEmail);
        return RestAssured.given()
                .header(AUTHORIZATION, BEARER + accessToken.accessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .put("/api/users")
                .then()
                .extract();
    }

    protected UserUpdate.Request 정상적인_회원_변경_정보() {
        return UserUpdate.Request.builder()
                .userEmail(OTHER_USER_EMAIL)
                .userBio(OTHER_USER_BIO)
                .userImage(OTHER_USER_IMAGE)
                .build();
    }

    protected UserUpdate.Request 비정상적인_회원_변경_정보(final String userEmail) {
        return UserUpdate.Request.builder()
                .userEmail(UserEmail.from(userEmail))
                .userBio(OTHER_USER_BIO)
                .userImage(OTHER_USER_IMAGE)
                .build();
    }
}
