package com.study.realworld.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.user.Service.UserService;
import com.study.realworld.user.controller.request.UserJoinRequest;
import com.study.realworld.user.controller.response.UserResponse;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
            .alwaysExpect(status().isOk())
            .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void joinTest() throws Exception {

        // setup
        UserJoinRequest request = new UserJoinRequest("username", "test@test.com", "password", null,
            null);
        User user = User.Builder()
            .username(new Username(request.getUsername()))
            .email(new Email(request.getEmail()))
            .password(new Password(request.getPassword()))
            .bio(request.getBio())
            .image(request.getImage())
            .build();

        when(userService.join(any())).thenReturn(user);

        // given
        final String URL = "/api/users";

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
        ;
    }
}