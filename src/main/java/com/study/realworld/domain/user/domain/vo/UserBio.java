package com.study.realworld.domain.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserBio {

    @Column(name = "user_bio")
    private String userBio;

    public static UserBio from(final String userBio) {
        return new UserBio(userBio);
    }

    @JsonValue
    public String value() {
        return userBio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBio userBio1 = (UserBio) o;
        return Objects.equals(userBio, userBio1.userBio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userBio);
    }
}
