package com.study.realworld.article.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.study.realworld.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class TitleTest {

    @Test
    void titleTest() {
        Title title = new Title();
    }

    @Test
    @DisplayName("title 길이가 50이 넘을 경우 exception이 발생되어야 한다.")
    void titleLengthExceptionTest() {

        // given
        StringBuilder sb = new StringBuilder();
        String plus = "A";
        for (int i=1; i<=51; i++){
            sb.append(plus);
        }
        String input = sb.toString();

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Title.of(input))
            .withMessageMatching(ErrorCode.INVALID_TITLE_LENGTH.getMessage());
    }

    @NullAndEmptySource
    @ParameterizedTest
    @DisplayName("title이 빈값이 들어올 경우 exception이 발생해야 한다.")
    void titleNullAndEmptyExceptionTest(String input) {


        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Title.of(input))
            .withMessageMatching(ErrorCode.INVALID_TITLE_NULL.getMessage());
    }

}