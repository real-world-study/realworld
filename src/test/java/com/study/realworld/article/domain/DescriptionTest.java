package com.study.realworld.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.study.realworld.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class DescriptionTest {
    @Test
    void deescriptionTest() {
        Description description = new Description();
    }

    @Test
    @DisplayName("description 길이가 255이 넘을 경우 exception이 발생되어야 한다.")
    void descriptionLengthExceptionTest() {

        // given
        StringBuilder sb = new StringBuilder();
        String plus = "A";
        for (int i=1; i<=256; i++){
            sb.append(plus);
        }
        String input = sb.toString();

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Description.of(input))
            .withMessageMatching(ErrorCode.INVALID_DESCRIPTION_LENGTH.getMessage());
    }

    @NullAndEmptySource
    @ParameterizedTest
    @DisplayName("description이 빈값이 들어올 경우 exception이 발생해야 한다.")
    void descriptionNullAndEmptyExceptionTest(String input) {

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Description.of(input))
            .withMessageMatching(ErrorCode.INVALID_DESCRIPTION_NULL.getMessage());
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void descriptionEqualsHashCodeTest() {

        // given
        String input = "slug";

        // when
        Description result = Description.of(input);

        // then
        assertThat(result)
            .isEqualTo(Description.of(input))
            .hasSameHashCodeAs(Description.of(input));
    }
}