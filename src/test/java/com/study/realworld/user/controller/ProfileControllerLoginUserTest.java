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

import com.study.realworld.testutil.PrincipalArgumentResolver;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
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
public class ProfileControllerLoginUserTest {

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController profileController;

    private MockMvc mockMvc;

    private User user;
    private User followee;

    @BeforeEach
    void beforeEach(RestDocumentationContextProvider restDocumentationContextProvider) {
        SecurityContextHolder.clearContext();
        mockMvc = MockMvcBuilders.standaloneSetup(profileController)
            .apply(documentationConfiguration(restDocumentationContextProvider))
            .setCustomArgumentResolvers(new PrincipalArgumentResolver())
            .alwaysExpect(status().isOk())
            .build();

        user = User.Builder()
            .id(1L)
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .build();

        followee = User.Builder()
            .id(2L)
            .profile(Username.of("jakefriend"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jakefriend@jake.jake"))
            .password(Password.of("jakejake"))
            .build();
    }

    @Test
    void getProfileByLoginTest() throws Exception {

        // setup
        String username = followee.username().value();
        ProfileResponse response = ProfileResponse.fromUserAndFollowing(followee, true);
        when(profileService.findProfile(user.id(), followee.username())).thenReturn(response);

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

            .andExpect(jsonPath("$.profile.username", is(followee.username().value())))
            .andExpect(jsonPath("$.profile.bio", is(followee.bio().value())))
            .andExpect(jsonPath("$.profile.image", is(nullValue())))
            .andExpect(jsonPath("$.profile.following", is(true)))
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
