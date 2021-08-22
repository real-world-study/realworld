package com.study.realworld.user.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.realworld.user.controller.dto.request.JoinDto;
import com.study.realworld.user.controller.dto.request.LoginDto;
import com.study.realworld.user.controller.dto.request.UpdateDto;
import com.study.realworld.user.controller.dto.response.UserInfo;
import com.study.realworld.user.entity.User;
import com.study.realworld.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private User getUser(String email) {
        return User.builder()
                   .email(email)
                   .username("DolphaGo")
                   .bio("hello")
                   .build();
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @DisplayName("SignIn Test")
    @Nested
    class Login {

        @DisplayName("SignIn Request")
        @Test
        void login() throws Exception {
            LoginDto loginDto = LoginDto.create("dolphago@test.net", "1q2w3e4r");

            User user = getUser(loginDto.getEmail());
            String accessToken = "accessToken";
            UserInfo userInfo = UserInfo.create(user, accessToken);

            when(userService.login(any(LoginDto.class))).thenReturn(userInfo);

            mockMvc.perform(
                           post("/api/users/login")
                                   .content(objectMapper.writeValueAsString(loginDto))
                                   .contentType(APPLICATION_JSON))
                   .andExpect(status().isOk())
                   .andExpect(content().contentType(APPLICATION_JSON))
                   .andExpect(jsonPath("$.user.email", is(user.getEmail())))
                   .andExpect(jsonPath("$.user.username", is(user.getUsername())))
                   .andExpect(jsonPath("$.user.bio", is(user.getBio())))
                   .andExpect(jsonPath("$.user.token", is(accessToken)));
        }

        @ParameterizedTest
        @CsvSource({
                "dolphago@test.net, ",
                "dolphago@test.net, ' '",
                "dolphago@test.net, ''",
                "dolphago@.net, 1234",
        })
        void invalid_argument(String email, String password) throws Exception {
            LoginDto loginDto = LoginDto.create(email, password);
            mockMvc.perform(
                           post("/api/users/login")
                                   .content(objectMapper.writeValueAsString(loginDto))
                                   .contentType(APPLICATION_JSON))
                   .andExpect(status().isBadRequest());
        }
    }

    @DisplayName("SignUp Test")
    @Nested
    class Join {

        @DisplayName("SignUp Request")
        @Test
        void join() throws Exception {
            final JoinDto joinDto = JoinDto.create("dolphago@test.net", "DolphaGo", "1q2w3e4r");

            User user = getUser(joinDto.getEmail());
            String accessToken = "accessToken";
            UserInfo userInfo = UserInfo.create(user, accessToken);

            when(userService.join(any(JoinDto.class))).thenReturn(userInfo);

            mockMvc.perform(
                           post("/api/users")
                                   .content(objectMapper.writeValueAsString(joinDto))
                                   .contentType(APPLICATION_JSON))
                   .andExpect(status().isOk())
                   .andExpect(content().contentType(APPLICATION_JSON))
                   .andExpect(jsonPath("$.user.email", is("dolphago@test.net")))
                   .andExpect(jsonPath("$.user.username", is("DolphaGo")))
                   .andExpect(jsonPath("$.user.token", is("accessToken")));
        }

        @ParameterizedTest
        @CsvSource({
                "dolphago@test.net, DolphaGo, ",
                "dolphago@test.net, , 12345",
                ", DolphaGo, 12345",
                "abc@.net, DolphaGo, 12345",
                "abc@.net, '  ', 12345"
        })
        void invalid_argument(String email, String username, String password) throws Exception {
            final JoinDto joinDto = JoinDto.create(email, username, password);
            mockMvc.perform(
                           post("/api/users")
                                   .content(objectMapper.writeValueAsString(joinDto))
                                   .contentType(APPLICATION_JSON))
                   .andExpect(status().isBadRequest());
        }
    }

    @DisplayName("UserInfo Update Request")
    @Nested
    class Update {

        @DisplayName("update Request")
        @Test
        void update() throws Exception {
            final UpdateDto updateDto = UpdateDto.create("dolphago@test.net", "update-bio", "/path/image.png");
            final User updatedUser = getUser(updateDto.getEmail());
            updatedUser.update(updateDto.getEmail(), updateDto.getBio(), updateDto.getImage());
            final String accessToken = "accessToken";

            final UserInfo userInfo = UserInfo.create(updatedUser, accessToken);

            when(userService.update(any(UpdateDto.class))).thenReturn(userInfo);

            mockMvc.perform(
                           put("/api/user")
                                   .content(objectMapper.writeValueAsString(updateDto))
                                   .contentType(APPLICATION_JSON))
                   .andExpect(status().isOk())
                   .andExpect(content().contentType(APPLICATION_JSON))
                   .andExpect(jsonPath("$.user.email", is("dolphago@test.net")))
                   .andExpect(jsonPath("$.user.username", is("DolphaGo")))
                   .andExpect(jsonPath("$.user.bio", is(updateDto.getBio())))
                   .andExpect(jsonPath("$.user.image", is(updateDto.getImage())));
        }

        @ParameterizedTest
        @CsvSource({
                "dolphago@test.net, DolphaGo, ",
                "dolphago@test.net, ,12345",
                "dolphago@test.net, ' ', 12345",
                "dolphago@test.net, hello, ' '",
        })
        void invalid_argument(String email, String bio, String image) throws Exception {
            final UpdateDto updateDto = UpdateDto.create(email, bio, image);
            mockMvc.perform(
                           put("/api/user")
                                   .content(objectMapper.writeValueAsString(updateDto))
                                   .contentType(APPLICATION_JSON))
                   .andExpect(status().isBadRequest());
        }
    }

}
