package com.study.realworld.user.controller;

import static com.study.realworld.user.controller.ApiDocumentUtils.getDocumentRequest;
import static com.study.realworld.user.controller.ApiDocumentUtils.getDocumentResponse;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.study.realworld.global.security.JwtAuthentication;
import com.study.realworld.global.security.JwtService;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Image;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.Profile;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.UserService;
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
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach(RestDocumentationContextProvider restDocumentationContextProvider) {
        SecurityContextHolder.clearContext();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
            .apply(documentationConfiguration(restDocumentationContextProvider))
            .alwaysExpect(status().isOk())
            .build();
    }

    @Test
    void joinTest() throws Exception {

        // setup
        User user = User.Builder()
            .profile(Profile.Builder()
                .username(Username.of("username"))
                .build())
            .email(Email.of("test@test.com"))
            .password(Password.of("password"))
            .build();

        when(userService.join(any())).thenReturn(user);
        when(jwtService.createToken(user)).thenReturn("token");

        // given
        final String URL = "/api/users";
        final String content = "{\"user\":{\"username\":\"" + "username"
            + "\",\"email\":\"" + "test@test.com"
            + "\",\"password\":\"" + "password"
            + "\"}}";

        // when
        ResultActions resultActions = mockMvc.perform(post(URL)
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.user.username", is("username")))
            .andExpect(jsonPath("$.user.email", is("test@test.com")))
            .andExpect(jsonPath("$.user.bio", is(nullValue())))
            .andExpect(jsonPath("$.user.image", is(nullValue())))
            .andExpect(jsonPath("$.user.token", is("token")))
            .andDo(document("user-join",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("user.username").type(JsonFieldType.STRING).description("유저이름"),
                    fieldWithPath("user.email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("user.password").type(JsonFieldType.STRING).description("패스워드"),
                    fieldWithPath("user.bio").type(JsonFieldType.STRING).description("bio")
                        .optional(),
                    fieldWithPath("user.image").type(JsonFieldType.STRING).description("이미지")
                        .optional()
                ),
                responseFields(
                    fieldWithPath("user.email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("user.token").type(JsonFieldType.STRING).description("로그인 토큰"),
                    fieldWithPath("user.username").type(JsonFieldType.STRING).description("유저이름"),
                    fieldWithPath("user.bio").type(JsonFieldType.STRING).description("bio")
                        .optional(),
                    fieldWithPath("user.image").type(JsonFieldType.STRING).description("이미지")
                        .optional()
                )
            ))
        ;
    }


    @Test
    void loginTest() throws Exception {

        // setup
        User user = User.Builder()
            .profile(Profile.Builder()
                .username(Username.of("username"))
                .build())
            .email(Email.of("test@test.com"))
            .password(Password.of("password"))
            .build();

        when(userService.login(any(), any())).thenReturn(user);
        when(jwtService.createToken(user)).thenReturn("token");

        // given
        final String URL = "/api/users/login";
        final String content = "{\"user\":{\"email\":\"" + "test@test.com"
            + "\",\"password\":\"" + "password"
            + "\"}}";

        // when
        ResultActions resultActions = mockMvc.perform(post(URL)
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.user.username", is("username")))
            .andExpect(jsonPath("$.user.email", is("test@test.com")))
            .andExpect(jsonPath("$.user.bio", is(nullValue())))
            .andExpect(jsonPath("$.user.image", is(nullValue())))
            .andExpect(jsonPath("$.user.token", is("token")))
            .andDo(document("user-login",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("user.email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("user.password").type(JsonFieldType.STRING).description("패스워드")
                ),
                responseFields(
                    fieldWithPath("user.email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("user.token").type(JsonFieldType.STRING).description("로그인 토큰"),
                    fieldWithPath("user.username").type(JsonFieldType.STRING).description("유저이름"),
                    fieldWithPath("user.bio").type(JsonFieldType.STRING).description("bio")
                        .optional(),
                    fieldWithPath("user.image").type(JsonFieldType.STRING).description("이미지")
                        .optional()
                )
            ))
        ;
    }

    @Test
    void getCurrentUserTest() throws Exception {

        // setup
        User user = User.Builder()
            .profile(Profile.Builder()
                .username(Username.of("username"))
                .build())
            .email(Email.of("test@test.com"))
            .password(Password.of("password"))
            .build();
        when(userService.findById(any())).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(1L, "token"));

        // given
        final String URL = "/api/user";

        // when
        AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(1L, "1q2w3e4r");
        ResultActions resultActions = mockMvc.perform(get(URL)
            .principal(authenticationToken)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.user.username", is("username")))
            .andExpect(jsonPath("$.user.email", is("test@test.com")))
            .andExpect(jsonPath("$.user.bio", is(nullValue())))
            .andExpect(jsonPath("$.user.image", is(nullValue())))
            .andExpect(jsonPath("$.user.token", is("token")))
            .andDo(document("user-get-current",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("user.email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("user.token").type(JsonFieldType.STRING).description("로그인 토큰"),
                    fieldWithPath("user.username").type(JsonFieldType.STRING).description("유저이름"),
                    fieldWithPath("user.bio").type(JsonFieldType.STRING).description("bio")
                        .optional(),
                    fieldWithPath("user.image").type(JsonFieldType.STRING).description("이미지")
                        .optional()
                )
            ))
        ;
    }

    @Test
    void updateTest() throws Exception {

        // setup
        User user = User.Builder()
            .profile(Profile.Builder()
                .username(Username.of("usernameChange"))
                .bio(Bio.of("bioChange"))
                .image(Image.of("imageChange"))
                .build())
            .email(Email.of("change@change.com"))
            .password(Password.of("passwordChange"))
            .build();

        when(userService.update(any(), any())).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(1L, "token"));

        // given
        final String URL = "/api/user";
        final String content = "{\"user\":{\"username\":\"" + "usernameChange"
            + "\",\"email\":\"" + "change@change.com"
            + "\",\"password\":\"" + "passwordChange"
            + "\",\"bio\":\"" + "bioChange"
            + "\",\"image\":\"" + "imageChange"
            + "\"}}";

        // when
        ResultActions resultActions = mockMvc.perform(put(URL)
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.user.username", is("usernameChange")))
            .andExpect(jsonPath("$.user.email", is("change@change.com")))
            .andExpect(jsonPath("$.user.bio", is("bioChange")))
            .andExpect(jsonPath("$.user.image", is("imageChange")))
            .andExpect(jsonPath("$.user.token", is("token")))
            .andDo(document("user-update",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("user.username").type(JsonFieldType.STRING).description("변경유저이름"),
                    fieldWithPath("user.email").type(JsonFieldType.STRING).description("변경이메일"),
                    fieldWithPath("user.password").type(JsonFieldType.STRING).description("변경패스워드"),
                    fieldWithPath("user.bio").type(JsonFieldType.STRING).description("변경bio")
                        .optional(),
                    fieldWithPath("user.image").type(JsonFieldType.STRING).description("변경이미지")
                        .optional()
                ),
                responseFields(
                    fieldWithPath("user.email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("user.token").type(JsonFieldType.STRING).description("로그인 토큰"),
                    fieldWithPath("user.username").type(JsonFieldType.STRING).description("유저이름"),
                    fieldWithPath("user.bio").type(JsonFieldType.STRING).description("bio")
                        .optional(),
                    fieldWithPath("user.image").type(JsonFieldType.STRING).description("이미지")
                        .optional()
                )
            ))
        ;
    }

}