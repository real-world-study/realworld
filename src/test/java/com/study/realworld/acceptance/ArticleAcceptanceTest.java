package com.study.realworld.acceptance;

import com.study.realworld.domain.article.domain.vo.ArticleBody;
import com.study.realworld.domain.article.domain.vo.ArticleDescription;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.article.domain.vo.ArticleTitle;
import com.study.realworld.domain.article.dto.ArticleInfo;
import com.study.realworld.domain.article.dto.ArticleSave;
import com.study.realworld.domain.auth.dto.Login;
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
        final ArticleSave.Response articleResponse = response.as(ArticleSave.Response.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articleResponse.articleSlug().articleSlug()).isEqualTo("how-to-train-your-dragon"),
                () -> assertThat(articleResponse.articleTitle().articleTitle()).isEqualTo("How to train your dragon"),
                () -> assertThat(articleResponse.articleDescription().articleDescription()).isEqualTo("Ever wonder how?"),
                () -> assertThat(articleResponse.articleBody().articleBody()).isEqualTo("You have to believe"),
                () -> assertThat(articleResponse.tags()).isEqualTo(List.of("reactjs", "angularjs", "dragons")),
                () -> assertThat(articleResponse.createdAt()).isNotNull(),
                () -> assertThat(articleResponse.updatedAt()).isNotNull(),
                () -> assertThat(articleResponse.favorited()).isEqualTo(false),
                () -> assertThat(articleResponse.favoritesCount()).isEqualTo(0),
                () -> assertThat(articleResponse.author().userName().userName()).isEqualTo("woozi"),
                () -> assertThat(articleResponse.author().userBio()).isNull(),
                () -> assertThat(articleResponse.author().userImage()).isNull(),
                () -> assertThat(articleResponse.author().following()).isEqualTo(false)
        );
    }

    @Test
    void 슬러그를_통해_게시글을_조회한다_인가안됨() {
        final Login.Response loginResponse = 로그인_되어있음(user1.userEmail().userEmail());
        final ExtractableResponse<Response> articleSaveResponse = 정상적인_게시글_등록_요청(loginResponse.accessToken());
        final ArticleSave.Response articleResponse = articleSaveResponse.as(ArticleSave.Response.class);
        final ExtractableResponse<Response> articleFindResponse = 정상적인_인가되지_않은_게시글_조회_요청(loginResponse.accessToken(), articleResponse.articleSlug());
        final ArticleInfo articleInfo = articleFindResponse.as(ArticleInfo.class);

        assertAll(
                () -> assertThat(articleFindResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articleInfo.articleSlug().articleSlug()).isEqualTo("how-to-train-your-dragon"),
                () -> assertThat(articleInfo.articleTitle().articleTitle()).isEqualTo("How to train your dragon"),
                () -> assertThat(articleInfo.articleDescription().articleDescription()).isEqualTo("Ever wonder how?"),
                () -> assertThat(articleInfo.articleBody().articleBody()).isEqualTo("You have to believe"),
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
        final ExtractableResponse<Response> articleSaveResponse = 정상적인_게시글_등록_요청(loginResponse.accessToken());
        final ArticleSave.Response articleResponse = articleSaveResponse.as(ArticleSave.Response.class);
        final ExtractableResponse<Response> articleFindResponse = 정상적인_인가된_게시글_조회_요청(loginResponse.accessToken(), articleResponse.articleSlug());
        final ArticleInfo articleInfo = articleFindResponse.as(ArticleInfo.class);

        assertAll(
                () -> assertThat(articleFindResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articleInfo.articleSlug().articleSlug()).isEqualTo("how-to-train-your-dragon"),
                () -> assertThat(articleInfo.articleTitle().articleTitle()).isEqualTo("How to train your dragon"),
                () -> assertThat(articleInfo.articleDescription().articleDescription()).isEqualTo("Ever wonder how?"),
                () -> assertThat(articleInfo.articleBody().articleBody()).isEqualTo("You have to believe"),
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

    protected ArticleSave.Request 정상적인_게시글_정보() {
        return ArticleSave.Request.builder()
                .articleTitle(ArticleTitle.from("How to train your dragon"))
                .articleDescription(ArticleDescription.from("Ever wonder how?"))
                .articleBody(ArticleBody.from("You have to believe"))
                .tags(List.of("reactjs", "angularjs", "dragons"))
                .build();
    }
}
