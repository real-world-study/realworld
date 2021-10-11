package com.study.realworld.domain.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.domain.auth.dto.ResponseToken;
import com.study.realworld.domain.auth.infrastructure.TokenProvider;
import com.study.realworld.domain.user.application.UserJoinService;
import com.study.realworld.domain.user.application.UserUpdateService;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.*;
import com.study.realworld.domain.user.dto.UserJoinRequest;
import com.study.realworld.domain.user.dto.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.study.realworld.documentation.ApiDocumentUtils.getDocumentRequest;
import static com.study.realworld.documentation.ApiDocumentUtils.getDocumentResponse;
import static com.study.realworld.domain.user.domain.vo.BioTest.BIO;
import static com.study.realworld.domain.user.domain.vo.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.vo.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.vo.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.vo.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.vo.PasswordTest.PASSWORD_ENCODER;
import static com.study.realworld.domain.user.domain.persist.UserTest.userBuilder;
import static com.study.realworld.domain.user.dto.UserJoinRequestTest.userJoinRequest;
import static com.study.realworld.domain.user.dto.UserUpdateRequestTest.userUpdateRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com") // (1)
@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
class UserApiDocumentTest {

    @Mock private UserJoinService userJoinService;
    @Mock private UserUpdateService userUpdateService;
    @Mock private TokenProvider tokenProvider;
    @InjectMocks private UserApi userApi;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.standaloneSetup(userApi)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
        objectMapper = new ObjectMapper();
    }

    @DisplayName("UserRestController 인스턴스의 join() 단위 테스트")
    @Test
    void join_test() throws Exception {
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE)).encode(PASSWORD_ENCODER);
        final ResponseToken responseToken = new ResponseToken("accessToken", "refreshToken");
        final UserJoinRequest userJoinRequest = userJoinRequest(new Name(USERNAME), new Email(EMAIL), new Password(PASSWORD));
        final String userJoinRequestString = objectMapper.writeValueAsString(userJoinRequest);

        given(userJoinService.join(any())).willReturn(user);
        given(tokenProvider.createToken(any())).willReturn(responseToken);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJoinRequestString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("users-join", // (4)
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("user.username").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("user.email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("user.password").type(JsonFieldType.STRING).description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("user.username").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("user.email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("user.bio").type(JsonFieldType.STRING).description("설명"),
                                fieldWithPath("user.image").type(JsonFieldType.STRING).description("사진"),
                                fieldWithPath("user.token.accessToken").type(JsonFieldType.STRING).description("access 토큰"),
                                fieldWithPath("user.token.refreshToken").type(JsonFieldType.STRING).description("refresh 토큰")
                        )
                ));
    }

    @DisplayName("UserRestController 인스턴스의 update() 단위 테스트")
    @Test
    void update_test() throws Exception {
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE)).encode(PASSWORD_ENCODER);
        final ResponseToken responseToken = new ResponseToken("accessToken", "refreshToken");
        final UserUpdateRequest userUpdateRequest = userUpdateRequest(new Email("changeEmail"), new Bio("ChangeBio"), new Image("changeImage"));
        String userUpdateRequestString = objectMapper.writeValueAsString(userUpdateRequest);

        given(userUpdateService.update(any(), any())).willReturn(user);
        given(tokenProvider.createToken(any())).willReturn(responseToken);

        mockMvc.perform(put("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userUpdateRequestString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("users-update", // (4)
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("user.email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("user.bio").type(JsonFieldType.STRING).description("설명"),
                                fieldWithPath("user.image").type(JsonFieldType.STRING).description("사진")
                        ),
                        responseFields(
                                fieldWithPath("user.username").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("user.email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("user.bio").type(JsonFieldType.STRING).description("설명"),
                                fieldWithPath("user.image").type(JsonFieldType.STRING).description("사진"),
                                fieldWithPath("user.token.accessToken").type(JsonFieldType.STRING).description("access 토큰"),
                                fieldWithPath("user.token.refreshToken").type(JsonFieldType.STRING).description("refresh 토큰")
                        )
                ));
    }

}