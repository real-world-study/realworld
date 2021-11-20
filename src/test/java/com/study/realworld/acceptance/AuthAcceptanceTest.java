package com.study.realworld.acceptance;

import com.study.realworld.domain.auth.dto.Login;
import com.study.realworld.domain.user.domain.vo.UserEmail;
import com.study.realworld.domain.user.dto.UserJoin;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.study.realworld.domain.user.domain.vo.util.UserVOFixture.USER_NAME;
import static com.study.realworld.domain.user.domain.vo.util.UserVOFixture.USER_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Auth 관련 인수 테스트")
class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    void 로그인_성공() {
        final String expected = "kwj1270@gmail.com";
        final UserJoin.Response userJoinResponse = 회원_가입_되어있음(expected);
        final ExtractableResponse<Response> response = 로그인_요청(userJoinResponse.userEmail().value());
        final Login.Response loginResponse = response.as(Login.Response.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(loginResponse.userEmail().value()).isEqualTo(expected)
        );
    }

    protected Login.Request 정상적인_로그인_정보(final String userEmail) {
        return Login.Request.builder()
                .userEmail(UserEmail.from(userEmail))
                .userPassword(USER_PASSWORD)
                .build();
    }

    protected UserJoin.Response 회원_가입_되어있음(final String email) {
        final ExtractableResponse<Response> response = 회원_가입_요청(email);
        return response.as(UserJoin.Response.class);
    }

    protected ExtractableResponse<Response> 로그인_요청(final String userEmail) {
        final Login.Request request = 정상적인_로그인_정보(userEmail);
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/users/login")
                .then()
                .extract();
    }

    protected ExtractableResponse<Response> 회원_가입_요청(final String email) {
        final UserJoin.Request request = 정상적인_회원가입_정보(email);
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/users")
                .then()
                .extract();
    }

    protected UserJoin.Request 정상적인_회원가입_정보(final String email) {
        return UserJoin.Request.builder()
                .userEmail(UserEmail.from(email))
                .userName(USER_NAME)
                .userPassword(USER_PASSWORD)
                .build();
    }
}
