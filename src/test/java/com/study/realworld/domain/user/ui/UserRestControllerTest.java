package com.study.realworld.domain.user.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.domain.user.application.UserJoinService;
import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.Name;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.dto.UserJoinRequest;
import com.study.realworld.domain.user.dto.UserJoinResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.UserTest.*;
import static com.study.realworld.domain.user.dto.UserJoinRequestTest.userJoinRequest;
import static com.study.realworld.domain.user.dto.UserJoinResponseTest.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @AutoConfigureMockMvc 이건 뭘까요?
// slice 테스트
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserJoinService userJoinService;

    @DisplayName("UserRestController 인스턴스의 join() 테스트")
    @Test
    void join_test() throws Exception {
        final UserJoinRequest userJoinRequest = userJoinRequest(new Name(USERNAME), new Email(EMAIL), PASSWORD);
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), PASSWORD, BIO, IMAGE);
        final UserJoinResponse userJoinResponse = userJoinResponse(user);

        given(userJoinService.join(any())).willReturn(userJoinResponse);

        final String targetPath = "/api/users";
        final String userJoinRequestString = objectMapper.writeValueAsString(userJoinRequest);

        mockMvc.perform(post(targetPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJoinRequestString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}