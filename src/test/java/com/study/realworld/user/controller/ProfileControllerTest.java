package com.study.realworld.user.controller;

import static com.study.realworld.user.controller.ApiDocumentUtils.getDocumentRequest;
import static com.study.realworld.user.controller.ApiDocumentUtils.getDocumentResponse;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Image;
import com.study.realworld.user.domain.Profile;
import com.study.realworld.user.domain.Username;
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
class ProfileControllerTest {

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController profileController;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach(RestDocumentationContextProvider restDocumentationContextProvider) {
        SecurityContextHolder.clearContext();
        mockMvc = MockMvcBuilders.standaloneSetup(profileController)
            .apply(documentationConfiguration(restDocumentationContextProvider))
            .alwaysExpect(status().isOk())
            .build();
    }

    @Test
    void getProfileTest() throws Exception {

        // setup
        String username = "username";
        Profile profile = Profile.Builder()
            .username(Username.of(username))
            .bio(Bio.of("bio"))
            .image(Image.of("image"))
            .following(false)
            .build();
        when(profileService.findProfile(any(), any(Username.class))).thenReturn(profile);

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

            .andExpect(jsonPath("$.profile.username", is("username")))
            .andExpect(jsonPath("$.profile.bio", is("bio")))
            .andExpect(jsonPath("$.profile.image", is("image")))
            .andExpect(jsonPath("$.profile.following", is(false)))
            .andDo(document("get-profile",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("username").description("검색 유저 이름")
                ),
                responseFields(
                    fieldWithPath("profile.username").type(JsonFieldType.STRING).description("유저이름"),
                    fieldWithPath("profile.bio").type(JsonFieldType.STRING).description("bio").optional(),
                    fieldWithPath("profile.image").type(JsonFieldType.STRING).description("이미지").optional(),
                    fieldWithPath("profile.following").type(JsonFieldType.BOOLEAN).description("팔로우 여부")
                )
            ))
        ;
    }

    @Test
    void followTest() throws Exception {

        // setup
        String username = "username";
        Profile profile = Profile.Builder()
            .username(Username.of(username))
            .bio(Bio.of("bio"))
            .image(Image.of("image"))
            .following(true).build();
        when(profileService.followUser(any(), any(Username.class))).thenReturn(profile);

        // given
        final String URL = "/api/profiles/{username}/follow";

        // when
        ResultActions resultActions = mockMvc.perform(post(URL, username)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.profile.username", is("username")))
            .andExpect(jsonPath("$.profile.bio", is("bio")))
            .andExpect(jsonPath("$.profile.image", is("image")))
            .andExpect(jsonPath("$.profile.following", is(true)))
            .andDo(document("get-profile",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("username").description("검색 유저 이름")
                ),
                responseFields(
                    fieldWithPath("profile.username").type(JsonFieldType.STRING).description("유저이름"),
                    fieldWithPath("profile.bio").type(JsonFieldType.STRING).description("bio").optional(),
                    fieldWithPath("profile.image").type(JsonFieldType.STRING).description("이미지").optional(),
                    fieldWithPath("profile.following").type(JsonFieldType.BOOLEAN).description("팔로우 여부")
                )
            ))
        ;
    }

    @Test
    void unfollowTest() throws Exception {

        // setup
        String username = "username";
        Profile profile = Profile.Builder()
            .username(Username.of(username))
            .bio(Bio.of("bio"))
            .image(Image.of("image"))
            .following(false).build();
        when(profileService.unfollowUser(any(), any(Username.class))).thenReturn(profile);

        // given
        final String URL = "/api/profiles/{username}/follow";

        // when
        ResultActions resultActions = mockMvc.perform(delete(URL, username)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.profile.username", is("username")))
            .andExpect(jsonPath("$.profile.bio", is("bio")))
            .andExpect(jsonPath("$.profile.image", is("image")))
            .andExpect(jsonPath("$.profile.following", is(false)))
            .andDo(document("get-profile",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("username").description("검색 유저 이름")
                ),
                responseFields(
                    fieldWithPath("profile.username").type(JsonFieldType.STRING).description("유저이름"),
                    fieldWithPath("profile.bio").type(JsonFieldType.STRING).description("bio").optional(),
                    fieldWithPath("profile.image").type(JsonFieldType.STRING).description("이미지").optional(),
                    fieldWithPath("profile.following").type(JsonFieldType.BOOLEAN).description("팔로우 여부")
                )
            ))
        ;
    }

}