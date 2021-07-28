package com.study.realworld.user.controller.request;

import com.study.realworld.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.study.realworld.user.controller.request.UserJoinRequest.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserJoinRequestTest {

    @Mock
    private UserJoinRequest userJoinRequest;

    @Test
    void userJoinRequestTest() {
        UserJoinRequest userJoinRequest = new UserJoinRequest();
        assertThat(userJoinRequest.getUsername()).isNull();
        assertThat(userJoinRequest.getEmail()).isNull();
        assertThat(userJoinRequest.getPassword()).isNull();
        assertThat(userJoinRequest.getBio()).isNull();
        assertThat(userJoinRequest.getImage()).isNull();
    }

    @Test
    void userJoinRequestFromTest() {

        // given
        when(userJoinRequest.getUsername()).thenReturn("username");
        when(userJoinRequest.getEmail()).thenReturn("test@test.com");
        when(userJoinRequest.getPassword()).thenReturn("password");
        when(userJoinRequest.getBio()).thenReturn("bio");
        when(userJoinRequest.getImage()).thenReturn("image");

        // when
        User user = from(userJoinRequest);

        // then
        assertThat(user.getUsername().toString()).isEqualTo("username");
        assertThat(user.getEmail().toString()).isEqualTo("test@test.com");
        assertThat(user.getPassword()).isNotNull();
        assertThat(user.getBio()).isEqualTo("bio");
        assertThat(user.getImage()).isEqualTo("image");
    }

}