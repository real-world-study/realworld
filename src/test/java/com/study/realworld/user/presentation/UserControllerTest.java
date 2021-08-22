package com.study.realworld.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.core.configuration.SecurityConfig;
import com.study.realworld.core.domain.user.entity.User;
import com.study.realworld.core.jwt.JwtAccessDeniedHandler;
import com.study.realworld.core.jwt.JwtAuthenticationEntryPoint;
import com.study.realworld.core.jwt.TokenProvider;
import com.study.realworld.user.application.UserService;
import com.study.realworld.user.presentation.model.UserLoginRequest;
import com.study.realworld.user.presentation.model.UserRegisterRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Jeongjoon Seo
 */
@ExtendWith(SpringExtension.class)
//@Formatter:off
@WebMvcTest({UserController.class, SecurityConfig.class, JwtAuthenticationEntryPoint.class, JwtAccessDeniedHandler.class})
//@Formatter:on
class UserControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenProvider tokenProvider;

    @Qualifier("jacksonObjectMapper")
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService, tokenProvider))
                                 .addFilters(new CharacterEncodingFilter("UTF-8", true))
                                 .build();
    }

    @Test
    @DisplayName("회원가입 테스트")
    void registerTest() throws Exception {
        // given
        final String username = "찬스";
        final String email = "chance@chance.com";
        final String password = "chance";
        final LocalDateTime now = LocalDateTime.now();
        given(userService.register(any())).willReturn(User.builder()
                                                          .username(username)
                                                          .email(email)
                                                          .password(password)
                                                          .createdAt(now)
                                                          .build());

        UserRegisterRequest request = new UserRegisterRequest(username, email, password);

        String content = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/api/users").content(content)
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        // then
        then(userService).should().register(any());
    }

    @Test
    @DisplayName("로그인 기능")
    void loginTest() throws Exception {
        // given
        final String email = "chance@chance.com";
        final String password = "chance";

        given(userService.login(any())).willReturn(User.builder().email(email).password(password).build());

        UserLoginRequest request = new UserLoginRequest(email, password);

        String content = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/api/users/login").content(content)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        // then
        then(userService).should().login(any());
    }
}
