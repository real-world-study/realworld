package com.study.realworld.global.exception;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ErrorResponseTest {

    @Test
    void errorResponseTest() {
        ErrorResponse errorResponse = new ErrorResponse();
    }


    @Test
    @DisplayName("RuntimeException을 받아 toJson메소드를 실행하면 원하는 형태의 json string을 반환한다.")
    void toJsonTest() throws JsonProcessingException {

        // given
        RuntimeException runtimeException = new RuntimeException("test toJson method.");
        String expected = "{\"errors\":{\"body\":[\"test toJson method.\"]}}";

        // when
        String result = ErrorResponse.toJson(runtimeException);

        // then
        assertThat(result).isEqualTo(expected);
    }

}