package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.domain.user.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public final class UserJoinRequest {

    @NotEmpty @NotBlank
    @JsonProperty("username")
    private String username;

    @Email @NotEmpty @NotBlank
    @JsonProperty("email")
    private String email;

    @NotEmpty @NotBlank
    @JsonProperty("password")
    private String password;

    private UserJoinRequest() { }

    // 테스트용 오버로딩 생성자 -> 주관적인 생각으로 이런 상황은 오버라이드로 유연성을 주는게 좋다고 생각합니다.
    UserJoinRequest(final String username, final String email, final String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public final User toEntity() {
        return User.Builder()
                .email(email)
                .username(username)
                .password(password)
                .build();
    }

    @Override
    public final String toString() {
        return "UserJoinRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
