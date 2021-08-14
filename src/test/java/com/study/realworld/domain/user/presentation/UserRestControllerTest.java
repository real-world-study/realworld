package com.study.realworld.domain.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.domain.user.application.UserJoinService;
import com.study.realworld.domain.user.domain.*;
import com.study.realworld.domain.user.dto.UserJoinRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.study.realworld.domain.user.domain.BioTest.BIO;
import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD_ENCODER;
import static com.study.realworld.domain.user.domain.UserTest.userBuilder;
import static com.study.realworld.domain.user.dto.UserJoinRequestTest.userJoinRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 시큐리티 필터 제거
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private UserJoinService userJoinService;

    @DisplayName("UserRestController 인스턴스의 join() 테스트")
    @Test
    void join_test() throws Exception {
        final UserJoinRequest userJoinRequest = userJoinRequest(new Name(USERNAME), new Email(EMAIL), new Password(PASSWORD));
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE)).encode(PASSWORD_ENCODER);
        given(userJoinService.join(any())).willReturn(user);

        final String userJoinRequestString = objectMapper.writeValueAsString(userJoinRequest);

        mockMvc.perform(post("/api/users")
                .contentType(APPLICATION_JSON)
                .content(userJoinRequestString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

}