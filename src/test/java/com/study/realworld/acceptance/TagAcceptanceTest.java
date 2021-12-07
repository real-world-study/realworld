package com.study.realworld.acceptance;

import com.study.realworld.domain.auth.dto.Login;
import com.study.realworld.domain.tag.domain.vo.TagName;
import com.study.realworld.domain.tag.dto.TagFindResponse;
import com.study.realworld.domain.tag.dto.TagSave;
import com.study.realworld.domain.user.dto.UserJoin;
import com.study.realworld.global.common.AccessToken;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("태그 관련 인수 테스트")
public class TagAcceptanceTest extends AcceptanceTest {

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
    void 태그를_등록한다() {
        final Login.Response loginUserResponse = 로그인_되어있음(user1.userEmail().userEmail());
        final ExtractableResponse<Response> response = 태그_등록_요청(loginUserResponse.accessToken());
        final TagSave.Response tagSaveResponse = response.as(TagSave.Response.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(tagSaveResponse.tagName()).isEqualTo(TagName.from("tagName"))
        );
    }

    @Test
    void 태그를_조회한다() {
        final Login.Response loginUserResponse = 로그인_되어있음(user1.userEmail().userEmail());
        태그_등록_요청(loginUserResponse.accessToken());
        final ExtractableResponse<Response> response = 태그_조회_요청();
        final TagFindResponse tagFindResponse = response.as(TagFindResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(tagFindResponse.tags()).containsExactly(TagName.from("tagName"))
        );
    }

    protected ExtractableResponse<Response> 태그_등록_요청(final AccessToken accessToken) {
        final TagSave.Request request = 정상적인_태그_정보();
        return RestAssured.given()
                .header(AUTHORIZATION, BEARER + accessToken.accessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(request)
                .post("/api/tags")
                .then()
                .extract();
    }

    protected ExtractableResponse<Response> 태그_조회_요청() {
        return RestAssured.given()
                .when()
                .get("/api/tags")
                .then()
                .extract();
    }

    protected TagSave.Request 정상적인_태그_정보() {
        return TagSave.Request.from(TagName.from("tagName"));
    }
}
