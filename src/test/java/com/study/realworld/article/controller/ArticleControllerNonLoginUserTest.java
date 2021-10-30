package com.study.realworld.article.controller;

import static com.study.realworld.user.controller.ApiDocumentUtils.getDocumentRequest;
import static com.study.realworld.user.controller.ApiDocumentUtils.getDocumentResponse;
import static java.time.ZoneOffset.UTC;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.ArticleContent;
import com.study.realworld.article.domain.Body;
import com.study.realworld.article.domain.Description;
import com.study.realworld.article.domain.SlugTitle;
import com.study.realworld.article.domain.Title;
import com.study.realworld.article.service.ArticleService;
import com.study.realworld.tag.domain.Tag;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
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
public class ArticleControllerNonLoginUserTest {

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

    private MockMvc mockMvc;

    private User author;
    private ArticleContent articleContent1;
    private ArticleContent articleContent2;

    @BeforeEach
    void beforeEach(RestDocumentationContextProvider restDocumentationContextProvider) {
        SecurityContextHolder.clearContext();
        mockMvc = MockMvcBuilders.standaloneSetup(articleController)
            .apply(documentationConfiguration(restDocumentationContextProvider))
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .alwaysExpect(status().isOk())
            .build();

        author = User.Builder()
            .id(1L)
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .build();

        articleContent1 = ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of("How to train your dragon")))
            .description(Description.of("Ever wonder how?"))
            .body(Body.of("It takes a Jacobian"))
            .tags(Arrays.asList(Tag.of("dragons"), Tag.of("training")))
            .build();

        articleContent2 = ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of("How to train your dragon 2")))
            .description(Description.of("So toothless"))
            .body(Body.of("It a dragon"))
            .tags(Arrays.asList(Tag.of("dragons"), Tag.of("training")))
            .build();
    }

    @Test
    void getArticleTest() throws Exception {

        // setup
        Article article = Article.from(articleContent1, author);
        OffsetDateTime now = OffsetDateTime.now();
        ReflectionTestUtils.setField(article, "createdAt", now);
        ReflectionTestUtils.setField(article, "updatedAt", now);

        when(articleService.findBySlug(article.slug())).thenReturn(article);

        // given
        final String slug = article.slug().slug();
        final String URL = "/api/articles/{slug}";

        // when
        ResultActions resultActions = mockMvc.perform(get(URL, slug)
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
            .andExpect(jsonPath("$.article.tagList[0]", is(article.tags().get(0).name())))
            .andExpect(jsonPath("$.article.tagList[1]", is(article.tags().get(1).name())))
            .andExpect(jsonPath("$.article.createdAt",
                is(article.updatedAt().format(ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(UTC)))))
            .andExpect(jsonPath("$.article.updatedAt",
                is(article.updatedAt().format(ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(UTC)))))
            .andExpect(jsonPath("$.article.favorited", is(article.isFavorited())))
            .andExpect(jsonPath("$.article.favoritesCount", is(article.favoritesCount())))

            .andExpect(jsonPath("$.article.author.username", is(article.author().username().value())))
            .andExpect(jsonPath("$.article.author.bio", is(article.author().bio().value())))
            .andExpect(jsonPath("$.article.author.image", is(nullValue())))
            .andExpect(jsonPath("$.article.author.following", is(false)))

            .andDo(document("article-find",
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
    void getArticlesTest() throws Exception {

        // setup
        List<Article> articleList = new ArrayList<>();
        Article article1 = Article.from(articleContent1, author);
        OffsetDateTime now = OffsetDateTime.now();
        ReflectionTestUtils.setField(article1, "createdAt", now);
        ReflectionTestUtils.setField(article1, "updatedAt", now);
        articleList.add(article1);

        Article article2 = Article.from(articleContent1, author);
        now = OffsetDateTime.now();
        ReflectionTestUtils.setField(article2, "createdAt", now);
        ReflectionTestUtils.setField(article2, "updatedAt", now);
        articleList.add(article2);

        int offset = 0;
        int limit = 2;
        Page<Article> articles = new PageImpl<>(articleList.subList(0, 2), PageRequest.of(offset, limit), articleList.size());
        when(articleService.findAllArticles(any(), eq("dragons"), eq("jake"))).thenReturn(articles);

        // given
        final String URL = "/api/articles";

        Article expected = articleList.get(0);

        // when
        ResultActions resultActions = mockMvc.perform(get(URL)
            .param("offset", String.valueOf(offset))
            .param("limit", String.valueOf(limit))
            .param("tag", "dragons")
            .param("author", "jake")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.articles.size()", is(limit)))

            .andExpect(jsonPath("$.articles.[0].slug", is(expected.slug().slug())))
            .andExpect(jsonPath("$.articles.[0].title", is(expected.title().title())))
            .andExpect(jsonPath("$.articles.[0].description", is(expected.description().description())))
            .andExpect(jsonPath("$.articles.[0].body", is(expected.body().body())))
            .andExpect(jsonPath("$.articles.[0].tagList.[0]", is(expected.tags().get(0).name())))
            .andExpect(jsonPath("$.articles.[0].tagList.[1]", is(expected.tags().get(1).name())))
            .andExpect(jsonPath("$.articles.[0].createdAt",
                is(expected.createdAt().format(ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(UTC)))))
            .andExpect(jsonPath("$.articles.[0].updatedAt",
                is(expected.updatedAt().format(ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(UTC)))))
            .andExpect(jsonPath("$.articles.[0].favorited", is(expected.isFavorited())))
            .andExpect(jsonPath("$.articles.[0].favoritesCount", is(expected.favoritesCount())))

            .andExpect(jsonPath("$.articles.[0].author.username", is(expected.author().username().value())))
            .andExpect(jsonPath("$.articles.[0].author.bio", is(expected.author().bio().value())))
            .andExpect(jsonPath("$.articles.[0].author.image", is(nullValue())))
            .andExpect(jsonPath("$.articles.[0].author.following", is(false)))

            .andExpect(jsonPath("$.articlesCount", is(limit)))

            .andDo(document("articles-find",
                getDocumentRequest(),
                getDocumentResponse(),
                requestParameters(
                    parameterWithName("offset").description("offset/page number of articles[default=20]").optional(),
                    parameterWithName("limit").description("limit number of articles[default=0]").optional(),
                    parameterWithName("tag").description("filter by tag").optional(),
                    parameterWithName("author").description("filter by author username").optional(),
                    parameterWithName("favorited").description("filter by favorited user").optional()
                ),
                responseFields(
                    fieldWithPath("articles").type(JsonFieldType.ARRAY).description("articles list"),

                    fieldWithPath("articles[].slug").type(JsonFieldType.STRING).description("article's slug"),
                    fieldWithPath("articles[].title").type(JsonFieldType.STRING).description("article's title"),
                    fieldWithPath("articles[].description").type(JsonFieldType.STRING).description("article's description"),
                    fieldWithPath("articles[].body").type(JsonFieldType.STRING).description("article's body"),
                    fieldWithPath("articles[].tagList").type(JsonFieldType.ARRAY).description("article's tag's list"),
                    fieldWithPath("articles[].createdAt").type(JsonFieldType.STRING).description("article's create time"),
                    fieldWithPath("articles[].updatedAt").type(JsonFieldType.STRING).description("article's update time"),
                    fieldWithPath("articles[].favorited").type(JsonFieldType.BOOLEAN).description("login user's favoriting"),
                    fieldWithPath("articles[].favoritesCount").type(JsonFieldType.NUMBER).description("article's favoriting count"),

                    fieldWithPath("articles[].author.username").type(JsonFieldType.STRING).description("author's username"),
                    fieldWithPath("articles[].author.bio").type(JsonFieldType.STRING).description("author's bio").optional(),
                    fieldWithPath("articles[].author.image").type(JsonFieldType.STRING).description("author's image").optional(),
                    fieldWithPath("articles[].author.following").type(JsonFieldType.BOOLEAN).description("author's following"),
                    fieldWithPath("articlesCount").type(JsonFieldType.NUMBER).description("article list's size")
                )
            ))
        ;
    }

}
