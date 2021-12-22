package com.study.realworld.acceptance;

import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.article.dto.ArticleSave;
import com.study.realworld.domain.auth.dto.Login;
import com.study.realworld.domain.favorite.dto.FavoriteInfo;
import com.study.realworld.domain.favorite.dto.UnFavoriteInfo;
import com.study.realworld.domain.user.dto.UserJoin;
import com.study.realworld.global.common.AccessToken;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Set;

import static com.study.realworld.domain.tag.util.TagFixture.TAG_NAMES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("좋아요 관련 인수 테스트")
public class FavoriteAcceptanceTest extends AcceptanceTest {

    private UserJoin.Response user1;
    private UserJoin.Response user2;

    private ArticleAcceptanceTest articleAcceptanceTest = new ArticleAcceptanceTest();
    private FollowAcceptanceTest followAcceptanceTest = new FollowAcceptanceTest();
    private TagAcceptanceTest tagAcceptanceTest = new TagAcceptanceTest();

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
    void 게시글에_좋아요를_누른다() {
        final Login.Response user1 = 로그인_되어있음(this.user1.userEmail().userEmail());
        final ArticleSave.Response articleSaveResponse = articleAcceptanceTest.정상적인_게시글_등록되어_있음(user1.accessToken());
        final Login.Response user2 = 로그인_되어있음(this.user2.userEmail().userEmail());
        final ExtractableResponse<Response> response = 좋아요_요청(user2.accessToken(), articleSaveResponse.articleSlug());
        final FavoriteInfo favoriteResponse = response.as(FavoriteInfo.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(favoriteResponse.articleSlug()).isEqualTo(articleSaveResponse.articleSlug()),
                () -> assertThat(favoriteResponse.articleTitle()).isEqualTo(articleSaveResponse.articleTitle()),
                () -> assertThat(favoriteResponse.articleBody()).isEqualTo(articleSaveResponse.articleBody()),
                () -> assertThat(favoriteResponse.articleDescription()).isEqualTo(articleSaveResponse.articleDescription()),
                () -> assertThat(favoriteResponse.favorited()).isTrue(),
                () -> assertThat(favoriteResponse.favoritesCount()).isEqualTo(1L),
                () -> assertThat(favoriteResponse.tagNames()).isEqualTo(TAG_NAMES),
                () -> assertThat(favoriteResponse.createdAt()).isNotNull(),
                () -> assertThat(favoriteResponse.updatedAt()).isNotNull(),
                () -> assertThat(favoriteResponse.authorInfo().userName()).isEqualTo(articleSaveResponse.author().userName()),
                () -> assertThat(favoriteResponse.authorInfo().userBio()).isEqualTo(articleSaveResponse.author().userBio()),
                () -> assertThat(favoriteResponse.authorInfo().userImage()).isEqualTo(articleSaveResponse.author().userImage()),
                () -> assertThat(favoriteResponse.authorInfo().following()).isFalse()
        );
    }

    @Test
    void 게시글에_좋아요를_취소한다() {
        final Login.Response user1 = 로그인_되어있음(this.user1.userEmail().userEmail());
        final ArticleSave.Response articleSaveResponse = articleAcceptanceTest.정상적인_게시글_등록되어_있음(user1.accessToken());
        final Login.Response user2 = 로그인_되어있음(this.user2.userEmail().userEmail());
        final FavoriteInfo favoriteInfo = 좋아요_되어있음(user2.accessToken(), articleSaveResponse.articleSlug());
        final ExtractableResponse<Response> response = 좋아요_취소_요청(user2.accessToken(), favoriteInfo.articleSlug());
        final UnFavoriteInfo unFavoriteResponse = response.as(UnFavoriteInfo.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(unFavoriteResponse.articleSlug()).isEqualTo(articleSaveResponse.articleSlug()),
                () -> assertThat(unFavoriteResponse.articleTitle()).isEqualTo(articleSaveResponse.articleTitle()),
                () -> assertThat(unFavoriteResponse.articleBody()).isEqualTo(articleSaveResponse.articleBody()),
                () -> assertThat(unFavoriteResponse.articleDescription()).isEqualTo(articleSaveResponse.articleDescription()),
                () -> assertThat(unFavoriteResponse.favorited()).isFalse(),
                () -> assertThat(unFavoriteResponse.favoritesCount()).isEqualTo(0),
                () -> assertThat(unFavoriteResponse.tagNames()).isEqualTo(TAG_NAMES),
                () -> assertThat(unFavoriteResponse.createdAt()).isNotNull(),
                () -> assertThat(unFavoriteResponse.updatedAt()).isNotNull(),
                () -> assertThat(unFavoriteResponse.authorInfo().userName()).isEqualTo(articleSaveResponse.author().userName()),
                () -> assertThat(unFavoriteResponse.authorInfo().userBio()).isEqualTo(articleSaveResponse.author().userBio()),
                () -> assertThat(unFavoriteResponse.authorInfo().userImage()).isEqualTo(articleSaveResponse.author().userImage()),
                () -> assertThat(unFavoriteResponse.authorInfo().following()).isFalse()
        );
    }

    protected FavoriteInfo 좋아요_되어있음(final AccessToken accessToken, final ArticleSlug articleSlug) {
        final ExtractableResponse<Response> response = 좋아요_요청(accessToken, articleSlug);
        return response.as(FavoriteInfo.class);
    }

    protected ExtractableResponse<Response> 좋아요_요청(final AccessToken accessToken, final ArticleSlug articleSlug) {
        return RestAssured.given()
                .header(AUTHORIZATION, BEARER + accessToken.accessToken())
                .when()
                .post(String.format("/api/articles/%s/favorite", articleSlug.articleSlug()))
                .then()
                .extract();
    }

    protected ExtractableResponse<Response> 좋아요_취소_요청(final AccessToken accessToken, final ArticleSlug articleSlug) {
        return RestAssured.given()
                .header(AUTHORIZATION, BEARER + accessToken.accessToken())
                .when()
                .delete(String.format("/api/articles/%s/favorite", articleSlug.articleSlug()))
                .then()
                .extract();
    }
}
