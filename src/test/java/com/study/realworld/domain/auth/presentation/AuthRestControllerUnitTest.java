package com.study.realworld.domain.auth.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.domain.auth.application.AuthLoginService;
import com.study.realworld.domain.auth.application.JwtUserDetailsService;
import com.study.realworld.domain.auth.application.JwtUserDetailsServiceTest;
import com.study.realworld.domain.auth.dto.LoginRequest;
import com.study.realworld.domain.auth.dto.ResponseToken;
import com.study.realworld.domain.auth.infrastructure.TokenProvider;
import com.study.realworld.domain.user.domain.*;
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

import static com.study.realworld.domain.auth.dto.LoginRequestTest.loginRequest;
import static com.study.realworld.domain.user.domain.BioTest.BIO;
import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthRestControllerUnitTest {

    @Mock private AuthLoginService authLoginService;
    @Mock private JwtUserDetailsService jwtUserDetailsService;
    @Mock private TokenProvider tokenProvider;
    @InjectMocks private AuthRestController authRestController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authRestController).build();
        objectMapper = new ObjectMapper();
    }

    @DisplayName("AuthController 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final AuthRestController authRestController = new AuthRestController(authLoginService, jwtUserDetailsService, tokenProvider);

        assertAll(
                () -> assertThat(authRestController).isNotNull(),
                () -> assertThat(authRestController).isExactlyInstanceOf(AuthRestController.class)
        );
    }

    @DisplayName("AuthController 인스턴스 login 테스트")
    @Test
    void login_test() throws Exception {
        final User user = UserTest.userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        final LoginRequest loginRequest = loginRequest(new Email(EMAIL), new Password(PASSWORD));
        final String loginRequestString = objectMapper.writeValueAsString(loginRequest);
        final UserDetails userDetails = JwtUserDetailsServiceTest.mapToSecurityUser(user);
        final ResponseToken responseToken = new ResponseToken("accessToken", "refreshToken");

        doReturn(user).when(authLoginService).login(any(), any());
        doReturn(userDetails).when(jwtUserDetailsService).loadUserByUsername(any());
        doReturn(responseToken).when(tokenProvider).createToken(any());

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestString))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}