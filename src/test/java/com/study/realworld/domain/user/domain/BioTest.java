package com.study.realworld.domain.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

}