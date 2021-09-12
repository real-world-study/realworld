package com.study.realworld.domain.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.domain.auth.infrastructure.TokenProvider;
import com.study.realworld.domain.user.application.UserJoinService;
import com.study.realworld.domain.user.application.UserUpdateService;
import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.Name;
import com.study.realworld.domain.user.domain.Password;
import com.study.realworld.domain.user.dto.UserJoinRequest;
import com.study.realworld.domain.user.error.exception.DuplicatedEmailException;
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

import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.dto.UserJoinRequestTest.userJoinRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserRestControllerFailTest {

    @Mock
    private UserJoinService userJoinService;
    @Mock
    private UserUpdateService userUpdateService;
    @Mock
    private TokenProvider tokenProvider;
    @InjectMocks
    private UserRestController userRestController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController)
                .setControllerAdvice(new UserExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @DisplayName("UserRestController 의 join() 호출시 이메일 중복으로 인한 실패 테스트")
    @Test
    void join_fail_test() throws Exception {
        final String duplicatedEmailJson = duplicatedEmailJson();
        final UserJoinRequest userJoinRequest = userJoinRequest(new Name(USERNAME), new Email(EMAIL), new Password(PASSWORD));
        final String userJoinRequestString = objectMapper.writeValueAsString(userJoinRequest);

        given(userJoinService.join(any())).willThrow(new DuplicatedEmailException(EMAIL));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJoinRequestString))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(duplicatedEmailJson))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    private static String duplicatedEmailJson() {
        return "{\n" + "  \"errors\": {\n" +
                "    \"body\": [\n" +
                "      \"Email is Duplication\"\n" +
                "    ]\n" +
                "  }\n" +
                "}";
    }

}
