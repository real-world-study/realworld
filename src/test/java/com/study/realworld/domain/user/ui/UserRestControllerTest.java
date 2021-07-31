package com.study.realworld.domain.user.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.domain.user.application.UserJoiningService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.study.realworld.domain.user.dto.UserJoinRequestTest.USER_JOIN_REQUEST;
import static com.study.realworld.domain.user.dto.UserJoinResponseTest.USER_JOIN_RESPONSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @AutoConfigureMockMvc 이건 뭘까요?
@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private UserJoiningService userJoiningService;

    @DisplayName("UserRestController 인스턴스의 join() 테스트")
    @Test
    void join_test() throws Exception {
        given(userJoiningService.join(any())).willReturn(USER_JOIN_RESPONSE);

        final String targetPath = "/users";
        final String userJoinRequestString = objectMapper.writeValueAsString(USER_JOIN_REQUEST);

        mockMvc.perform(post(targetPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJoinRequestString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}