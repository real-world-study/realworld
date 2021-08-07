package com.study.realworld.user.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.dto.UserJoinRequest;
import com.study.realworld.user.dto.UserLoginRequest;
import com.study.realworld.user.dto.UserResponse;
import com.study.realworld.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String BIO = "bio";
    private static final String IMAGE = "image";

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

//    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .build();
    }

    @Test
    void POST_api_users() throws Exception {

        // given
        final String URL = "/api/users";

        final UserJoinRequest request = UserJoinRequest.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        final User user = User.builder()
                .id(ID)
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        final UserResponse response = UserResponse.builder()
                .email(EMAIL)
                .token(null)
                .username(USERNAME)
                .bio(BIO)
                .image(IMAGE)
                .build();

        /**
         * org.mockito.exceptions.base.MockitoException:
         * The used MockMaker SubclassByteBuddyMockMaker does not support the creation of static mocks
         *
         * Mockito's inline mock maker supports static mocks based on the Instrumentation API.
         * You can simply enable this mock mode, by placing the 'mockito-inline' artifact where you are currently using 'mockito-core'.
         * Note that Mockito's inline mock maker is not supported on Android.
         */
//        mockStatic(UserResponse.class)
//                .when(() -> UserResponse.of(any(), anyString()))
//                .thenReturn(response);

        when(userService.save(any()))
                .thenReturn(user);

        // when
        ResultActions result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
//                .andDo(print())
                ;

        // https://www.infoworld.com/article/3543268/junit-5-tutorial-part-2-unit-testing-spring-mvc-with-junit-5.html?page=3
        // then
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
    void POST_api_users_login() throws Exception {

        // given
        final String URL = "/api/users/login";

        final UserLoginRequest request = UserLoginRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        final User user = User.builder()
                .id(ID)
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();

        final UserResponse response = UserResponse.builder()
                .email(EMAIL)
                .token(null)
                .username(USERNAME)
                .bio(BIO)
                .image(IMAGE)
                .build();

        when(userService.login(any()))
                .thenReturn(user);

        // when
        ResultActions result = mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
//                .andDo(print())
                ;

        // then
        verify(userService).login(any());
        result
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.user.email", is(EMAIL)))
                .andExpect(jsonPath("$.user.username", is(USERNAME)))
                .andExpect(jsonPath("$.user.bio", is(BIO)))
                .andExpect(jsonPath("$.user.image", is(IMAGE)));
    }

}
