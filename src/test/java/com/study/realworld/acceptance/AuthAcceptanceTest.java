package com.study.realworld.acceptance;

import com.study.realworld.domain.auth.dto.Login;
import com.study.realworld.domain.user.dto.UserJoin;
import com.study.realworld.domain.user.error.UserErrorResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Auth 관련 인수 테스트")
class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    void 로그인_성공() {
        final String expected = "kwj1270@gmail.com";
        final UserJoin.Response userJoinResponse = 회원_가입_되어있음(expected);
        final ExtractableResponse<Response> response = 로그인_요청(userJoinResponse.userEmail().userEmail());
        final Login.Response loginResponse = response.as(Login.Response.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(loginResponse.userEmail().userEmail()).isEqualTo(expected)
        );
    }

    @Test
    void 로그인_실패_존재하지_않는_회원일_경우() {
        final String userEmail = "kwj1270@gmail.com";
        final String invalidUserEmail = "test@gmail.com";

        회원_가입_되어있음(userEmail);
        final ExtractableResponse<Response> response = 로그인_요청(invalidUserEmail);
        final UserErrorResponse errorResponse = 유저_예외_결과(response);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.body()).contains("Email is not found")
        );
    }
}
