package com.study.realworld.domain.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.domain.auth.application.JwtUserDetailsService;
import com.study.realworld.domain.auth.application.JwtUserDetailsServiceTest;
import com.study.realworld.domain.auth.dto.ResponseToken;
import com.study.realworld.domain.auth.infrastructure.TokenProvider;
import com.study.realworld.domain.user.application.UserJoinService;
import com.study.realworld.domain.user.domain.*;
import com.study.realworld.domain.user.dto.UserJoinRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserRestControllerUnitTest {

    @Mock private UserJoinService userJoinService;
    @Mock private JwtUserDetailsService jwtUserDetailsService;
    @Mock private TokenProvider tokenProvider;
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
        final UserJoinRequest userJoinRequest = userJoinRequest(new Name(USERNAME), new Email(EMAIL), new Password(PASSWORD));
        final String userJoinRequestString = objectMapper.writeValueAsString(userJoinRequest);
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE)).encode(PASSWORD_ENCODER);
        final UserDetails userDetails = JwtUserDetailsServiceTest.mapToSecurityUser(user);
        final ResponseToken responseToken = new ResponseToken("accessToken", "refreshToken");

        given(userJoinService.join(any())).willReturn(user);
        given(jwtUserDetailsService.loadUserByUsername(any())).willReturn(userDetails);
        given(tokenProvider.createToken(any())).willReturn(responseToken);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJoinRequestString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}