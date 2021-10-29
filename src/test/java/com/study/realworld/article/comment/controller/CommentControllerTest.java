package com.study.realworld.article.comment.controller;

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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.study.realworld.article.comment.domain.Comment;
import com.study.realworld.article.comment.domain.CommentBody;
import com.study.realworld.article.comment.service.CommentService;
import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.ArticleContent;
import com.study.realworld.article.domain.Body;
import com.study.realworld.article.domain.Description;
import com.study.realworld.article.domain.SlugTitle;
import com.study.realworld.article.domain.Title;
import com.study.realworld.tag.domain.Tag;
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
class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private MockMvc mockMvc;

    private User author;
    private Article article;

    @BeforeEach
    void beforeEach(RestDocumentationContextProvider restDocumentationContextProvider) {
        SecurityContextHolder.clearContext();
        mockMvc = MockMvcBuilders.standaloneSetup(commentController)
            .apply(documentationConfiguration(restDocumentationContextProvider))
            .alwaysExpect(status().isOk())
            .build();

        author = User.Builder()
            .profile(Username.of("username"), null, null)
            .email(Email.of("email@email.com"))
            .password(Password.of("password"))
            .build();
        ArticleContent articleContent = ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of("title title title")))
            .description(Description.of("test article description"))
            .body(Body.of("test article body"))
            .tags(Arrays.asList(Tag.of("tag1"), Tag.of("tag2")))
            .build();
        article = Article.from(articleContent, author);
    }

    @Test
    void createCommentTest() throws Exception {

        // setup
        Comment comment = Comment.from(CommentBody.of("His name was my name too."), author, article);
        ReflectionTestUtils.setField(comment, "id", 1L);

        OffsetDateTime now = OffsetDateTime.now();
        ReflectionTestUtils.setField(comment, "createdAt", now);
        ReflectionTestUtils.setField(comment, "updatedAt", now);

        when(commentService.createComment(any(), eq(article.slug()), any())).thenReturn(comment);

        // given
        final String URL = "/api/articles/{slug}/comments";
        final String content = "{\n"
            + "  \"comment\": {\n"
            + "    \"body\": \"His name was my name too.\"\n"
            + "  }\n"
            + "}";

        // when
        ResultActions resultActions = mockMvc.perform(post(URL, article.slug().slug())
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.comment.id", is(comment.id().intValue())))
            .andExpect(jsonPath("$.comment.createdAt",
                is(comment.createdAt().format(ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(UTC)))))
            .andExpect(jsonPath("$.comment.updatedAt",
                is(comment.updatedAt().format(ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(UTC)))))
            .andExpect(jsonPath("$.comment.body", is(comment.commentBody().body())))
            .andExpect(jsonPath("$.comment.author.username", is(article.author().username().value())))
            .andExpect(jsonPath("$.comment.author.bio", is(nullValue())))
            .andExpect(jsonPath("$.comment.author.image", is(nullValue())))
            .andExpect(jsonPath("$.comment.author.following", is(false)))

            .andDo(document("comment-create",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("comment.body").type(JsonFieldType.STRING).description("comment body")
                ),
                responseFields(
                    fieldWithPath("comment.id").type(JsonFieldType.NUMBER).description("created comment's id"),
                    fieldWithPath("comment.createdAt").type(JsonFieldType.STRING)
                        .description("created comment's create time"),
                    fieldWithPath("comment.updatedAt").type(JsonFieldType.STRING)
                        .description("created comment's update time"),
                    fieldWithPath("comment.body").type(JsonFieldType.STRING).description("created comment's body"),

                    fieldWithPath("comment.author.username").type(JsonFieldType.STRING)
                        .description("author's username"),
                    fieldWithPath("comment.author.bio").type(JsonFieldType.STRING).description("author's bio")
                        .optional(),
                    fieldWithPath("comment.author.image").type(JsonFieldType.STRING).description("author's image")
                        .optional(),
                    fieldWithPath("comment.author.following").type(JsonFieldType.BOOLEAN)
                        .description("author's following")
                )
            ))
        ;
    }
}