package com.study.realworld.comment.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.study.realworld.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

public class CommentBodyTest {

    @Test
    void bodyTest() {
        CommentBody body = new CommentBody();
    }

    @NullSource
    @ParameterizedTest
    @DisplayName("body가 null이 들어올 경우 exception이 발생해야 한다.")
    void bodyNullExceptionTest(String input) {

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> CommentBody.of(input))
            .withMessageMatching(ErrorCode.INVALID_COMMENT_BODY_NULL.getMessage());
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void bodyEqualsHashCodeTest() {

        // given
        String input = "body";

        // when
        CommentBody result = CommentBody.of(input);

        // then
        assertThat(result)
            .isEqualTo(CommentBody.of(input))
            .hasSameHashCodeAs(CommentBody.of(input));
    }

}
