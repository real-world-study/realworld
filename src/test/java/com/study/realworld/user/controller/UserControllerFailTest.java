package com.study.realworld.user.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.global.exception.GlobalExceptionHandler;
import com.study.realworld.security.JwtService;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
public class UserControllerFailTest {
    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach(RestDocumentationContextProvider restDocumentationContextProvider) {
        SecurityContextHolder.clearContext();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
            .apply(documentationConfiguration(restDocumentationContextProvider))
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void joinFailByDuplicatedUsernameTest() throws Exception {

        // setup
        User user = User.Builder()
            .username(Username.of("username"))
            .email(Email.of("test@test.com"))
            .password(Password.of("password"))
            .build();

        when(userService.join(any())).thenThrow(new BusinessException(ErrorCode.USERNAME_DUPLICATION));

        // given
        final String URL = "/api/users";
        final String content = "{\"user\":{\"username\":\"" + "username"
            + "\",\"email\":\"" + "test@test.com"
            + "\",\"password\":\"" + "password"
            + "\"}}";

        // when
        ResultActions resultActions = mockMvc.perform(post(URL)
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.errors.body.[0]", is("Duplicated username exists.")))
        ;
    }

    @Test
    void joinFailByInvalidEmailTest() throws Exception {

        // given
        final String URL = "/api/users";
        final String content = "{\"user\":{\"username\":\"" + "username"
            + "\",\"email\":\"" + "testtest.com"
            + "\",\"password\":\"" + "password"
            + "\"}}";

        // when
        ResultActions resultActions = mockMvc.perform(post(URL)
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.errors.body.[0]", is("address must be provided by limited pattern like 'xxx@xxx.xxx'.")))
        ;
    }

    @Test
    void joinFailByHttpMethodTest() throws Exception {

        // given
        final String URL = "/api/users";
        final String content = "{\"user\":{\"username\":\"" + "username"
            + "\",\"email\":\"" + "testtest.com"
            + "\",\"password\":\"" + "password"
            + "\"}}";

        // when
        ResultActions resultActions = mockMvc.perform(put(URL)
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.errors.body.[0]", is("Request method 'PUT' not supported")))
        ;
    }

}
