package com.study.realworld.domain.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserName {

    @NotBlank(message = "Name must have not blank")
    @Column(name = "user_name", length = 20, nullable = false)
    private String userName;

    public static UserName from(final String userName) {
        return new UserName(userName);
    }

    @JsonValue
    public String value() {
        return userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserName userName1 = (UserName) o;
        return Objects.equals(value(), userName1.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value());
    }
}
