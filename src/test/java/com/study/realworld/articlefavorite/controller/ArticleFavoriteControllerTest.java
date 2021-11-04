package com.study.realworld.articlefavorite.controller;

import static com.study.realworld.user.controller.ApiDocumentUtils.getDocumentRequest;
import static com.study.realworld.user.controller.ApiDocumentUtils.getDocumentResponse;
import static java.time.ZoneOffset.UTC;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.ArticleContent;
import com.study.realworld.article.domain.Body;
import com.study.realworld.article.domain.Description;
import com.study.realworld.article.domain.Slug;
import com.study.realworld.article.domain.SlugTitle;
import com.study.realworld.article.domain.Title;
import com.study.realworld.article.dto.response.ArticleResponse.ArticleResponseNested;
import com.study.realworld.articlefavorite.dto.response.ArticleFavoriteResponse;
import com.study.realworld.articlefavorite.service.ArticleFavoriteService;
import com.study.realworld.tag.domain.Tag;
import com.study.realworld.testutil.PrincipalArgumentResolver;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import java.time.OffsetDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
class ArticleFavoriteControllerTest {

    @Mock
    private ArticleFavoriteService articleFavoriteService;

    @InjectMocks
    private ArticleFavoriteController articleFavoriteController;

    private MockMvc mockMvc;

    private User user;
    private User author;
    private Article article;

    @BeforeEach
    void beforeEach(RestDocumentationContextProvider restDocumentationContextProvider) {
        SecurityContextHolder.clearContext();
        mockMvc = MockMvcBuilders.standaloneSetup(articleFavoriteController)
            .apply(documentationConfiguration(restDocumentationContextProvider))
            .setCustomArgumentResolvers(new PrincipalArgumentResolver())
            .alwaysExpect(status().isOk())
            .build();

        user = User.Builder()
            .id(1L)
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .build();

        author = User.Builder()
            .id(2L)
            .profile(Username.of("jakefriend"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jakefriend@jake.jake"))
            .password(Password.of("jakejake"))
            .build();

        ArticleContent articleContent = ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of("How to train your dragon")))
            .description(Description.of("Ever wonder how?"))
            .body(Body.of("It takes a Jacobian"))
            .tags(Arrays.asList(Tag.of("dragons"), Tag.of("training")))
            .build();
        article = Article.from(articleContent, author);
    }

    @Test
    void favoriteArticleTest() throws Exception {

        // setup
        OffsetDateTime now = OffsetDateTime.now();
        ReflectionTestUtils.setField(article, "createdAt", now);
        ReflectionTestUtils.setField(article, "updatedAt", now);
        user.favoriteArticle(article);

        Long userId = 1L;
        Slug slug = article.slug();
        when(articleFavoriteService.favoriteArticle(userId, slug))
            .thenReturn(ArticleFavoriteResponse.from(
                ArticleResponseNested.from(article, user, true, 1, false)
            ));

        // given
        final String URL = "/api/articles/{slug}/favorite";

        // when
        ResultActions resultActions = mockMvc.perform(post(URL, slug.slug())
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.article.slug", is(article.slug().slug())))
            .andExpect(jsonPath("$.article.title", is(article.title().title())))
            .andExpect(jsonPath("$.article.description", is(article.description().description())))
            .andExpect(jsonPath("$.article.body", is(article.body().body())))
            .andExpect(jsonPath("$.article.tagList[0]", is(article.tags().get(0))))
            .andExpect(jsonPath("$.article.tagList[1]", is(article.tags().get(1))))
            .andExpect(jsonPath("$.article.createdAt",
                is(article.updatedAt().format(ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(UTC)))))
            .andExpect(jsonPath("$.article.updatedAt",
                is(article.updatedAt().format(ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(UTC)))))
            .andExpect(jsonPath("$.article.favorited", is(true)))
            .andExpect(jsonPath("$.article.favoritesCount", is(1)))

            .andExpect(jsonPath("$.article.author.username", is(article.author().username().value())))
            .andExpect(jsonPath("$.article.author.bio", is(article.author().bio().value())))
            .andExpect(jsonPath("$.article.author.image", is(nullValue())))
            .andExpect(jsonPath("$.article.author.following", is(false)))

            .andDo(document("article-favorite",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("article.slug").type(JsonFieldType.STRING).description("article's slug"),
                    fieldWithPath("article.title").type(JsonFieldType.STRING).description("article's title"),
                    fieldWithPath("article.description").type(JsonFieldType.STRING).description("article's description"),
                    fieldWithPath("article.body").type(JsonFieldType.STRING).description("article's body"),
                    fieldWithPath("article.tagList").type(JsonFieldType.ARRAY).description("article's tag's list"),
                    fieldWithPath("article.createdAt").type(JsonFieldType.STRING).description("article's create time"),
                    fieldWithPath("article.updatedAt").type(JsonFieldType.STRING).description("article's update time"),
                    fieldWithPath("article.favorited").type(JsonFieldType.BOOLEAN).description("login user's favoriting"),
                    fieldWithPath("article.favoritesCount").type(JsonFieldType.NUMBER).description("article's favoriting count"),

                    fieldWithPath("article.author.username").type(JsonFieldType.STRING).description("author's username"),
                    fieldWithPath("article.author.bio").type(JsonFieldType.STRING).description("author's bio").optional(),
                    fieldWithPath("article.author.image").type(JsonFieldType.STRING).description("author's image").optional(),
                    fieldWithPath("article.author.following").type(JsonFieldType.BOOLEAN).description("author's following")
                )
            ))
        ;
    }

    @Test
    void unfavoriteArticleTest() throws Exception {

        // setup
        OffsetDateTime now = OffsetDateTime.now();
        ReflectionTestUtils.setField(article, "createdAt", now);
        ReflectionTestUtils.setField(article, "updatedAt", now);

        Long userId = 1L;
        Slug slug = article.slug();
        when(articleFavoriteService.favoriteArticle(userId, slug))
            .thenReturn(ArticleFavoriteResponse.from(
                ArticleResponseNested.from(article, user, false, 0, false)
            ));

        // given
        final String URL = "/api/articles/{slug}/favorite";

        // when
        ResultActions resultActions = mockMvc.perform(post(URL, slug.slug())
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.article.slug", is(article.slug().slug())))
            .andExpect(jsonPath("$.article.title", is(article.title().title())))
            .andExpect(jsonPath("$.article.description", is(article.description().description())))
            .andExpect(jsonPath("$.article.body", is(article.body().body())))
            .andExpect(jsonPath("$.article.tagList[0]", is(article.tags().get(0))))
            .andExpect(jsonPath("$.article.tagList[1]", is(article.tags().get(1))))
            .andExpect(jsonPath("$.article.createdAt",
                is(article.updatedAt().format(ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(UTC)))))
            .andExpect(jsonPath("$.article.updatedAt",
                is(article.updatedAt().format(ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(UTC)))))
            .andExpect(jsonPath("$.article.favorited", is(false)))
            .andExpect(jsonPath("$.article.favoritesCount", is(0)))

            .andExpect(jsonPath("$.article.author.username", is(article.author().username().value())))
            .andExpect(jsonPath("$.article.author.bio", is(article.author().bio().value())))
            .andExpect(jsonPath("$.article.author.image", is(nullValue())))
            .andExpect(jsonPath("$.article.author.following", is(false)))

            .andDo(document("article-unfavorite",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("article.slug").type(JsonFieldType.STRING).description("article's slug"),
                    fieldWithPath("article.title").type(JsonFieldType.STRING).description("article's title"),
                    fieldWithPath("article.description").type(JsonFieldType.STRING).description("article's description"),
                    fieldWithPath("article.body").type(JsonFieldType.STRING).description("article's body"),
                    fieldWithPath("article.tagList").type(JsonFieldType.ARRAY).description("article's tag's list"),
                    fieldWithPath("article.createdAt").type(JsonFieldType.STRING).description("article's create time"),
                    fieldWithPath("article.updatedAt").type(JsonFieldType.STRING).description("article's update time"),
                    fieldWithPath("article.favorited").type(JsonFieldType.BOOLEAN).description("login user's favoriting"),
                    fieldWithPath("article.favoritesCount").type(JsonFieldType.NUMBER).description("article's favoriting count"),

                    fieldWithPath("article.author.username").type(JsonFieldType.STRING).description("author's username"),
                    fieldWithPath("article.author.bio").type(JsonFieldType.STRING).description("author's bio").optional(),
                    fieldWithPath("article.author.image").type(JsonFieldType.STRING).description("author's image").optional(),
                    fieldWithPath("article.author.following").type(JsonFieldType.BOOLEAN).description("author's following")
                )
            ))
        ;
    }

}
