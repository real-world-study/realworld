package com.study.realworld.follow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.vo.Bio;
import com.study.realworld.user.domain.vo.Email;
import com.study.realworld.user.domain.vo.Password;
import com.study.realworld.user.domain.vo.Username;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FollowTest {

    private User user;
    private User followee;

    @BeforeEach
    void beforeEach() {
        user = User.Builder()
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .build();

        followee = User.Builder()
            .profile(Username.of("jakefriend"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jakefriend@jake.jake"))
            .password(Password.of("jakejake"))
            .build();
    }

    @Test
    void followTest() {
        Follow follow = new Follow();
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void followEqualsHashCodeTest() {

        // given & when
        Follow result = Follow.builder()
            .follower(user)
            .followee(followee)
            .build();

        // then
        assertThat(result)
            .isEqualTo(Follow.builder()
                .follower(user)
                .followee(followee)
                .build())
            .hasSameHashCodeAs(Follow.builder()
                .follower(user)
                .followee(followee)
                .build());
    }


}