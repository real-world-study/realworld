package com.study.realworld.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BioTest {

    @Test
    void bioTest() {
        Bio bio = new Bio();
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void bioEqualsHashCodeTest() {

        // given
        Bio bio = Bio.of("bio");
        Bio copyBio = Bio.of("bio");

        // when & then
        assertThat(bio)
            .isEqualTo(copyBio)
            .hasSameHashCodeAs(copyBio);
    }

    @Test
    @DisplayName("toString 테스트")
    void bioToStringTest() {

        // given
        String input = "bio";

        // when
        Bio bio = Bio.of(input);

        // then
        assertThat(bio.toString()).isEqualTo(input);
    }

}
