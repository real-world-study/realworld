package com.study.realworld.domain.user.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.domain.user.application.UserJoiningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.study.realworld.domain.user.dto.UserJoinRequestTest.USER_JOIN_REQUEST;
import static com.study.realworld.domain.user.dto.UserJoinResponseTest.USER_JOIN_RESPONSE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserRestControllerUnitTest {

    @Mock private UserJoiningService userJoiningService;
    @InjectMocks private UserRestController userRestController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
        objectMapper = new ObjectMapper();
    }

    @DisplayName("UserRestController 인스턴스의 join() 단위 테스트")
    @Test
    void join_test() throws Exception {
        given(userJoiningService.join(any())).willReturn(USER_JOIN_RESPONSE);

        final String userJoinRequestString = objectMapper.writeValueAsString(USER_JOIN_REQUEST);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJoinRequestString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}