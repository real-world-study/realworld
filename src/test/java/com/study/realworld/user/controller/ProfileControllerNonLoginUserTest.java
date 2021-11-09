package com.study.realworld.user.controller;

import static com.study.realworld.user.controller.ApiDocumentUtils.getDocumentRequest;
import static com.study.realworld.user.controller.ApiDocumentUtils.getDocumentResponse;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.vo.Bio;
import com.study.realworld.user.domain.vo.Email;
import com.study.realworld.user.domain.vo.Password;
import com.study.realworld.user.domain.vo.Username;
import com.study.realworld.user.dto.response.ProfileResponse;
import com.study.realworld.user.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
class ProfileControllerNonLoginUserTest {

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController profileController;

    private MockMvc mockMvc;

    private User user;

    @BeforeEach
    void beforeEach(RestDocumentationContextProvider restDocumentationContextProvider) {
        SecurityContextHolder.clearContext();
        mockMvc = MockMvcBuilders.standaloneSetup(profileController)
            .apply(documentationConfiguration(restDocumentationContextProvider))
            .alwaysExpect(status().isOk())
            .build();

        user = com.study.realworld.user.domain.User.Builder()
            .id(1L)
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .build();
    }

    @Test
    void getProfileByLoginTest() throws Exception {

        // setup
        String username = user.username().value();
        ProfileResponse response = ProfileResponse.fromUserAndFollowing(user, false);
        when(profileService.findProfile(user.username())).thenReturn(response);

        // given
        final String URL = "/api/profiles/{username}";

        // when
        ResultActions resultActions = mockMvc.perform(get(URL, username)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.profile.username", is(user.username().value())))
            .andExpect(jsonPath("$.profile.bio", is(user.bio().value())))
            .andExpect(jsonPath("$.profile.image", is(nullValue())))
            .andExpect(jsonPath("$.profile.following", is(false)))
            .andDo(document("get-profile-login",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("username").description("want to search user's username")
                ),
                responseFields(
                    fieldWithPath("profile.username").type(JsonFieldType.STRING).description("user's username"),
                    fieldWithPath("profile.bio").type(JsonFieldType.STRING).description("user's bio").optional(),
                    fieldWithPath("profile.image").type(JsonFieldType.STRING).description("user's image").optional(),
                    fieldWithPath("profile.following").type(JsonFieldType.BOOLEAN).description("user's is following")
                )
            ))
        ;
    }

}