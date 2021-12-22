package com.study.realworld.domain.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserEmail {

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email must have not blank")
    @Column(name = "user_email", length = 50, nullable = false, unique = true)
    private String userEmail;

    public static UserEmail from(final String userEmail) {
        return new UserEmail(userEmail);
    }

    @JsonValue
    public String userEmail() {
        return userEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEmail userEmail1 = (UserEmail) o;
        return Objects.equals(userEmail(), userEmail1.userEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail());
    }

}
