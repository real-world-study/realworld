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
public class UserImage {

    @Column(name = "user_image")
    private String userImage;

    public static UserImage from(final String userImage) {
        return new UserImage(userImage);
    }

    @JsonValue
    public String userImage() {
        return userImage;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserImage userImage = (UserImage) o;
        return Objects.equals(userImage(), userImage.userImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userImage());
    }

}
