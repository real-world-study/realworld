package com.study.realworld.user.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProfileTest {

    @Test
    void Profile() {
        Profile profile = new Profile();
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void profileEqualsHashCodeTest() {

        // given
        Profile profile = Profile.Builder()
            .username(Username.of("username"))
            .bio(Bio.of("bio"))
            .image(Image.of("image"))
            .build();
        Profile copyProfile = Profile.Builder()
            .username(Username.of("username"))
            .bio(Bio.of("bio"))
            .image(Image.of("image"))
            .build();

        // when & then
        assertThat(profile)
            .isEqualTo(copyProfile)
            .hasSameHashCodeAs(copyProfile);
    }

}
