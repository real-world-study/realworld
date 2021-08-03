package com.study.realworld.user.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.dto.UserJoinRequest;
import com.study.realworld.user.dto.UserLoginRequest;
import com.study.realworld.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String BIO = "bio";
    private static final String IMAGE = "image";

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void POST_api_users() throws Exception {

        // given
        UserJoinRequest request = UserJoinRequest.builder()
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        String url = "/api/users";

        // when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        // then
        User user = userService.findByEmail(EMAIL);
        assertAll(
                () -> assertEquals(EMAIL, user.getEmail()),
                () -> assertEquals(USERNAME, user.getUsername()),
                () -> assertTrue(user.matchesPassword(PASSWORD, passwordEncoder))
        );
    }

    @Test
    void POST_api_users_login() throws Exception {

        // given
        User userGiven = User.builder()
                .id(ID)
                .email(EMAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .bio(BIO)
                .image(IMAGE)
                .build();
        userGiven.encodePassword(passwordEncoder);
        userService.save(userGiven);

        UserLoginRequest request = UserLoginRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        String url = "/api/users/login";

        // when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        // then
        User user = userService.findByEmail(EMAIL);
        assertAll(
                () -> assertEquals(userGiven.getEmail(), user.getEmail()),
                () -> assertEquals(userGiven.getUsername(), user.getUsername())
        );
    }

}
