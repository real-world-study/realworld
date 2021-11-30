package com.study.realworld.acceptance;

import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.article.dto.ArticleInfo;
import com.study.realworld.domain.article.dto.ArticleSave;
import com.study.realworld.domain.article.dto.ArticleUpdate;
import com.study.realworld.domain.article.error.ArticleErrorResponse;
import com.study.realworld.domain.auth.dto.Login;
import com.study.realworld.domain.follow.error.FollowErrorResponse;
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

import java.util.List;

import static com.study.realworld.domain.article.util.ArticleFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("게시글 관련 인수 테스트")
public class ArticleAcceptanceTest extends AcceptanceTest {

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
    void 게시글을_등록한다() {
        final Login.Response loginResponse = 로그인_되어있음(user1.userEmail().userEmail());
        final ExtractableResponse<Response> response = 정상적인_게시글_등록_요청(loginResponse.accessToken());
        final ArticleSave.Response saveResponse = response.as(ArticleSave.Response.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(saveResponse.articleSlug()).isEqualTo(ARTICLE_SLUG),
                () -> assertThat(saveResponse.articleTitle()).isEqualTo(ARTICLE_TITLE),
                () -> assertThat(saveResponse.articleDescription()).isEqualTo(ARTICLE_DESCRIPTION),
                () -> assertThat(saveResponse.articleBody()).isEqualTo(ARTICLE_BODY),
                () -> assertThat(saveResponse.tags()).isEqualTo(List.of("reactjs", "angularjs", "dragons")),
                () -> assertThat(saveResponse.createdAt()).isNotNull(),
                () -> assertThat(saveResponse.updatedAt()).isNotNull(),
                () -> assertThat(saveResponse.favorited()).isEqualTo(false),
                () -> assertThat(saveResponse.favoritesCount()).isEqualTo(0),
                () -> assertThat(saveResponse.author().userName().userName()).isEqualTo("woozi"),
                () -> assertThat(saveResponse.author().userBio()).isNull(),
                () -> assertThat(saveResponse.author().userImage()).isNull(),
                () -> assertThat(saveResponse.author().following()).isEqualTo(false)
        );
    }

    @Test
    void 슬러그를_통해_게시글을_조회한다_인가안됨() {
        final Login.Response loginResponse = 로그인_되어있음(user1.userEmail().userEmail());
        final ArticleSave.Response saveResponse = 정상적인_게시글_등록되어_있음(loginResponse.accessToken());
        final ExtractableResponse<Response> response = 정상적인_인가되지_않은_게시글_조회_요청(loginResponse.accessToken(), saveResponse.articleSlug());
        final ArticleInfo articleInfo = response.as(ArticleInfo.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(saveResponse.articleSlug()).isEqualTo(ARTICLE_SLUG),
                () -> assertThat(saveResponse.articleTitle()).isEqualTo(ARTICLE_TITLE),
                () -> assertThat(saveResponse.articleDescription()).isEqualTo(ARTICLE_DESCRIPTION),
                () -> assertThat(saveResponse.articleBody()).isEqualTo(ARTICLE_BODY),
                () -> assertThat(articleInfo.tags()).isEqualTo(List.of("reactjs", "angularjs", "dragons")),
                () -> assertThat(articleInfo.createdAt()).isNotNull(),
                () -> assertThat(articleInfo.updatedAt()).isNotNull(),
                () -> assertThat(articleInfo.favorited()).isEqualTo(false),
                () -> assertThat(articleInfo.favoritesCount()).isEqualTo(0),
                () -> assertThat(articleInfo.author().userName().userName()).isEqualTo("woozi"),
                () -> assertThat(articleInfo.author().userBio()).isNull(),
                () -> assertThat(articleInfo.author().userImage()).isNull(),
                () -> assertThat(articleInfo.author().following()).isEqualTo(false)
        );
    }

    @Test
    void 슬러그를_통해_게시글을_조회한다_인가됨() {
        final Login.Response loginResponse = 로그인_되어있음(user1.userEmail().userEmail());
        final ArticleSave.Response saveResponse = 정상적인_게시글_등록되어_있음(loginResponse.accessToken());
        final ExtractableResponse<Response> response = 정상적인_인가된_게시글_조회_요청(loginResponse.accessToken(), saveResponse.articleSlug());
        final ArticleInfo articleInfo = response.as(ArticleInfo.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(saveResponse.articleSlug()).isEqualTo(ARTICLE_SLUG),
                () -> assertThat(saveResponse.articleTitle()).isEqualTo(ARTICLE_TITLE),
                () -> assertThat(saveResponse.articleDescription()).isEqualTo(ARTICLE_DESCRIPTION),
                () -> assertThat(saveResponse.articleBody()).isEqualTo(ARTICLE_BODY),
                () -> assertThat(articleInfo.tags()).isEqualTo(List.of("reactjs", "angularjs", "dragons")),
                () -> assertThat(articleInfo.createdAt()).isNotNull(),
                () -> assertThat(articleInfo.updatedAt()).isNotNull(),
                () -> assertThat(articleInfo.favorited()).isEqualTo(false),
                () -> assertThat(articleInfo.favoritesCount()).isEqualTo(0),
                () -> assertThat(articleInfo.author().userName().userName()).isEqualTo("woozi"),
                () -> assertThat(articleInfo.author().userBio()).isNull(),
                () -> assertThat(articleInfo.author().userImage()).isNull(),
                () -> assertThat(articleInfo.author().following()).isEqualTo(false)
        );
    }

    @Test
    void 게시글의_정보를_변경한다() {
        final Login.Response loginResponse = 로그인_되어있음(user1.userEmail().userEmail());
        final ArticleSave.Response saveResponse = 정상적인_게시글_등록되어_있음(loginResponse.accessToken());
        final ExtractableResponse<Response> response = 정상적인_게시글_변경_요청(loginResponse.accessToken(), saveResponse.articleSlug());
        final ArticleUpdate.Response updateResponse = response.as(ArticleUpdate.Response.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(updateResponse.articleSlug()).isEqualTo(OTHER_ARTICLE_SLUG),
                () -> assertThat(updateResponse.articleTitle()).isEqualTo(OTHER_ARTICLE_TITLE),
                () -> assertThat(updateResponse.articleDescription()).isEqualTo(OTHER_ARTICLE_DESCRIPTION),
                () -> assertThat(updateResponse.articleBody()).isEqualTo(OTHER_ARTICLE_BODY),
                () -> assertThat(updateResponse.tags()).isEqualTo(List.of("reactjs", "angularjs", "dragons")),
                () -> assertThat(updateResponse.createdAt()).isNotNull(),
                () -> assertThat(updateResponse.updatedAt()).isNotNull(),
                () -> assertThat(updateResponse.favorited()).isEqualTo(false),
                () -> assertThat(updateResponse.favoritesCount()).isEqualTo(0),
                () -> assertThat(updateResponse.author().userName().userName()).isEqualTo("woozi"),
                () -> assertThat(updateResponse.author().userBio()).isNull(),
                () -> assertThat(updateResponse.author().userImage()).isNull(),
                () -> assertThat(updateResponse.author().following()).isEqualTo(false)
        );
    }

    @Test
    void 다른_사람이_게시글의_정보를_변경하면_예외를_발생한다() {
        final Login.Response authorLoginResponse = 로그인_되어있음(user1.userEmail().userEmail());
        final Login.Response otherLoginResponse = 로그인_되어있음(user2.userEmail().userEmail());
        final ArticleSave.Response saveResponse = 정상적인_게시글_등록되어_있음(authorLoginResponse.accessToken());
        final ExtractableResponse<Response> response = 정상적인_게시글_변경_요청(otherLoginResponse.accessToken(), saveResponse.articleSlug());
        final ArticleErrorResponse ArticleErrorResponse = response.as(ArticleErrorResponse.class);

        assertThat(ArticleErrorResponse.body().get(0)).isEqualTo("login user is not author");
    }

    protected ArticleSave.Response 정상적인_게시글_등록되어_있음(final AccessToken accessToken) {
        final ExtractableResponse<Response> articleSaveResponse = 정상적인_게시글_등록_요청(accessToken);
        return articleSaveResponse.as(ArticleSave.Response.class);
    }

    protected ExtractableResponse<Response> 정상적인_게시글_등록_요청(final AccessToken accessToken) {
        final ArticleSave.Request request = 정상적인_게시글_정보();
        return RestAssured.given()
                .header(AUTHORIZATION, BEARER + accessToken.accessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/articles")
                .then()
                .extract();
    }

    protected ExtractableResponse<Response> 정상적인_인가되지_않은_게시글_조회_요청(final AccessToken accessToken, final ArticleSlug articleSlug) {
        return RestAssured.given()
                .when()
                .get(String.format("/api/articles/%s", articleSlug.articleSlug()))
                .then()
                .extract();
    }

    protected ExtractableResponse<Response> 정상적인_인가된_게시글_조회_요청(final AccessToken accessToken, final ArticleSlug articleSlug) {
        return RestAssured.given()
                .header(AUTHORIZATION, BEARER + accessToken.accessToken())
                .when()
                .get(String.format("/api/articles/%s", articleSlug.articleSlug()))
                .then()
                .extract();
    }

    protected ExtractableResponse<Response> 정상적인_게시글_변경_요청(final AccessToken accessToken, final ArticleSlug articleSlug) {
        final ArticleUpdate.Request request = 정상적인_게시글_변경_정보();
        return RestAssured.given()
                .header(AUTHORIZATION, BEARER + accessToken.accessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .put(String.format("/api/articles/%s", articleSlug.articleSlug()))
                .then()
                .extract();
    }

    private ArticleUpdate.Request 정상적인_게시글_변경_정보() {
        return ArticleUpdate.Request.builder()
                .articleTitle(OTHER_ARTICLE_TITLE)
                .articleBody(OTHER_ARTICLE_BODY)
                .articleDescription(OTHER_ARTICLE_DESCRIPTION)
                .build();
    }

    protected ArticleSave.Request 정상적인_게시글_정보() {
        return ArticleSave.Request.builder()
                .articleTitle(ARTICLE_TITLE)
                .articleBody(ARTICLE_BODY)
                .articleDescription(ARTICLE_DESCRIPTION)
                .tags(List.of("reactjs", "angularjs", "dragons"))
                .build();
    }
}
