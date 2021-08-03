package com.study.realworld.auth.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.auth.controller.request.UserLoginRequest;
import com.study.realworld.jwt.AuthenticationResponse;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class AuthenticatinControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthenticatinController authenticatinController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticatinController)
            .alwaysExpect(status().isOk())
            .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void loginTest() throws Exception {

        // setup
        UserLoginRequest request = new UserLoginRequest("test@test.com", "password");
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        User user = User.Builder().email(new Email("test@test.com"))
            .username(new Username("username")).build();
        AuthenticationResponse response = new AuthenticationResponse("token", user);
        when(authentication.getDetails()).thenReturn(response);

        // given
        final String URL = "/api/users/login";

        // when
        ResultActions resultActions = mockMvc.perform(post(URL)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.user.username", is("username")))
            .andExpect(jsonPath("$.user.email", is("test@test.com")))
            .andExpect(jsonPath("$.user.bio", is("null")))
            .andExpect(jsonPath("$.user.image", is("null")))
            .andExpect(jsonPath("$.user.token", is("token")))
        ;
    }
}