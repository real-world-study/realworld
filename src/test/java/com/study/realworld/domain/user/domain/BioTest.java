package com.study.realworld.domain.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class BioTest {

    @DisplayName("Bio 인스턴스 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final Bio bio = new Bio();

        assertAll(
                () -> assertThat(bio).isNotNull(),
                () -> assertThat(bio).isExactlyInstanceOf(Bio.class)
        );
    }

    @DisplayName("Bio 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final String bioString = "bio";
        final Bio bio = new Bio(bioString);

        assertAll(
                () -> assertThat(bio).isNotNull(),
                () -> assertThat(bio).isExactlyInstanceOf(Bio.class)
        );
    }

    @DisplayName("Bio 인스턴스 getter 테스트")
    @Test
    void getter_test() {
        final String bioString = "bio";
        final Bio bio = new Bio(bioString);

        assertThat(bio.bio()).isEqualTo(bioString);
    }

    @DisplayName("Bio 인스턴스 equals and hashcode 동등성 검증 테스트")
    @Test
    void equals_and_hashcode_test() {
        final String bioString = "bio";
        final Bio firstBio = new Bio(bioString);
        final Bio secondBio = new Bio(bioString);

        assertAll(
                () -> assertThat(firstBio).isEqualTo(secondBio),
                () -> assertThat(firstBio.hashCode()).isEqualTo(secondBio.hashCode())
        );
    }

    @DisplayName("Bio 인스턴스 toString 테스트")
    @Test
    void toString_test() {
        final String bioString = "bio";
        final Bio bio = new Bio(bioString);

        assertThat(bio.toString()).isEqualTo(String.format("Bio{bio='%s'}", bioString));
    }

}