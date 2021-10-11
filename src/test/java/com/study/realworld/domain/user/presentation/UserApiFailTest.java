package com.study.realworld.domain.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.domain.auth.infrastructure.TokenProvider;
import com.study.realworld.domain.user.application.UserJoinService;
import com.study.realworld.domain.user.application.UserUpdateService;
import com.study.realworld.domain.user.domain.vo.*;
import com.study.realworld.domain.user.dto.UserJoinRequest;
import com.study.realworld.domain.user.dto.UserUpdateRequest;
import com.study.realworld.domain.user.error.UserErrorCode;
import com.study.realworld.domain.user.error.exception.DuplicatedEmailException;
import com.study.realworld.domain.user.error.exception.IdentityNotFoundException;
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

import static com.study.realworld.domain.user.domain.vo.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.vo.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.vo.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.dto.UserJoinRequestTest.userJoinRequest;
import static com.study.realworld.domain.user.dto.UserUpdateRequestTest.userUpdateRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserApiFailTest {

    private static final String ERROR_JSON_FORMAT =
            "{\n" + "  \"errors\": {\n" +
            "    \"body\": [\n" +
            "      \"%s\"\n" +
            "    ]\n" +
            "  }\n" +
            "}";

    @Mock private UserJoinService userJoinService;
    @Mock private UserUpdateService userUpdateService;
    @Mock private TokenProvider tokenProvider;
    @InjectMocks private UserApi userApi;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userApi)
                .setControllerAdvice(new UserErrorHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @DisplayName("UserRestController 의 join() 호출시 이메일 중복으로 인한 실패 테스트")
    @Test
    void join_fail_test() throws Exception {
        final String duplicatedEmailJson = errorMessageJson(UserErrorCode.EMAIL_DUPLICATION.message());
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


    @DisplayName("UserRestController 인스턴스의 update() 단위 테스트")
    @Test
    void update_test() throws Exception {
        final String notFoundIdentityJson = errorMessageJson(UserErrorCode.IDENTITY_NOT_FOUND.message());
        final UserUpdateRequest userUpdateRequest = userUpdateRequest(new Email("changeEmail"), new Bio("ChangeBio"), new Image("changeImage"));
        final String userUpdateRequestString = objectMapper.writeValueAsString(userUpdateRequest);

        given(userUpdateService.update(any(), any())).willThrow(new IdentityNotFoundException(1L));

        mockMvc.perform(put("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userUpdateRequestString))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(notFoundIdentityJson))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    private static String errorMessageJson(final String body) {
        return String.format(ERROR_JSON_FORMAT, body);
    }

}
