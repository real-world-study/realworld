package com.study.realworld.user.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.config.auth.JwtProvider;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.dto.UserJoinRequest;
import com.study.realworld.user.dto.UserLoginRequest;
import com.study.realworld.user.dto.UserResponse;
import com.study.realworld.user.dto.UserUpdateRequest;
import com.study.realworld.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class
//        excludeFilters = @ComponentScan.Filter(
//                type = FilterType.REGEX, pattern = "com.study.realworld.config.auth.*")
)
class UserControllerTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String BIO = "bio";
    private static final String IMAGE = "image";
    private static final String TOKEN = "token";

    @MockBean
    private SecurityContext securityContext;

    @MockBean
    private Authentication authentication;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        SecurityContextHolder.clearContext();
    }

    @Test
    void POST_api_users_joinUser() throws Exception {
        // given
        final String URL = "/api/users";

        final UserJoinRequest request = UserJoinRequest.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        final User user = User.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        final UserResponse response = UserResponse.builder()
                .email(EMAIL)
                .token(TOKEN)
                .username(USERNAME)
                .bio(BIO)
                .image(IMAGE)
                .build();

        // when
        when(userService.save(any()))
                .thenReturn(user);

        // https://www.infoworld.com/article/3543268/junit-5-tutorial-part-2-unit-testing-spring-mvc-with-junit-5.html?page=3
        // then
        ResultActions result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print());

        verify(userService).save(any());
        result
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.user.email", is(EMAIL)))
                .andExpect(jsonPath("$.user.username", is(USERNAME)))
                .andExpect(jsonPath("$.user.bio", is(BIO)))
                .andExpect(jsonPath("$.user.image", is(IMAGE)));
    }

    @Test
    void POST_api_users_login_loginUser() throws Exception {
        // given
        final String URL = "/api/users/login";

        final UserLoginRequest request = UserLoginRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        final User user = User.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        // when
        when(userService.login(any()))
                .thenReturn(user);
        when(jwtProvider.generateJwtToken(any()))
                .thenReturn(TOKEN);

        // then
        ResultActions result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print());

        verify(userService).login(any());
        verify(jwtProvider).generateJwtToken(any());
        result
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.user.email", is(EMAIL)))
                .andExpect(jsonPath("$.user.token", is(TOKEN)))
                .andExpect(jsonPath("$.user.username", is(USERNAME)))
                .andExpect(jsonPath("$.user.bio", is(BIO)))
                .andExpect(jsonPath("$.user.image", is(IMAGE)));
    }

    @Test
    void GET_api_user_getUser() throws Exception {
        // given
        final String URL = "/api/user";

        final User user = User.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(EMAIL, TOKEN, Collections.emptyList()));

        // when
        when(userService.findByEmail(anyString()))
                .thenReturn(user);
        when(jwtProvider.generateJwtToken(any()))
                .thenReturn(TOKEN);

        // then
        ResultActions result = mvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Token " + TOKEN))
                .andDo(print());

        verify(userService).findByEmail(anyString());
        verify(jwtProvider).generateJwtToken(any());
        result
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.user.email", is(EMAIL)))
                .andExpect(jsonPath("$.user.token", is(TOKEN)))
                .andExpect(jsonPath("$.user.username", is(USERNAME)))
                .andExpect(jsonPath("$.user.bio", is(BIO)))
                .andExpect(jsonPath("$.user.image", is(IMAGE)));
    }

    @Test
    void PUT_api_user_updateUser() throws Exception {
        // given
        final String URL = "/api/user";

        final UserUpdateRequest request = UserUpdateRequest.builder()
                .email(EMAIL+EMAIL)
                .bio(BIO+BIO)
                .build();

        final User user = User.builder()
                .email(EMAIL+EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO+BIO)
                .image(IMAGE)
                .build();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(EMAIL, TOKEN, Collections.emptyList()));

        // when
        when(userService.update(any(), any()))
                .thenReturn(user);
        when(jwtProvider.generateJwtToken(any()))
                .thenReturn(TOKEN);

        // then
        ResultActions result = mvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "Token " + TOKEN))
                .andDo(print());

        verify(userService).update(any(), any());
        verify(jwtProvider).generateJwtToken(any());
        result
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.user.email", is(request.getEmail())))
                .andExpect(jsonPath("$.user.token", is(TOKEN)))
                .andExpect(jsonPath("$.user.username", is(USERNAME)))
                .andExpect(jsonPath("$.user.bio", is(request.getBio())))
                .andExpect(jsonPath("$.user.image", is(IMAGE)));

    }

}
