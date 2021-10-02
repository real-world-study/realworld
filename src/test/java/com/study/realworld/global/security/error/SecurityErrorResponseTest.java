package com.study.realworld.global.security.error;

import com.study.realworld.global.security.error.exception.SecurityErrorCodeNullPointerException;
import com.study.realworld.global.security.error.exception.UserDetailsNullPointerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class SecurityErrorResponseTest {

    @DisplayName("SecurityErrorResponse 인스턴스 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final SecurityErrorResponse securityErrorResponse = new SecurityErrorResponse();

        assertAll(
                () -> assertThat(securityErrorResponse).isNotNull(),
                () -> assertThat(securityErrorResponse).isInstanceOf(SecurityErrorResponse.class)
        );
    }

    @DisplayName("SecurityErrorResponse 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final SecurityErrorCode securityErrorCode = SecurityErrorCode.values(new UserDetailsNullPointerException());
        final SecurityErrorResponse securityErrorResponse = new SecurityErrorResponse(securityErrorCode);

        assertAll(
                () -> assertThat(securityErrorResponse).isNotNull(),
                () -> assertThat(securityErrorResponse).isInstanceOf(SecurityErrorResponse.class)
        );
    }

    @DisplayName("SecurityErrorResponse 인스턴스 null 예외처리 테스트")
    @Test
    void constructor_null_test() {
        assertThatThrownBy(() -> new SecurityErrorResponse(null))
                .isInstanceOf(SecurityErrorCodeNullPointerException.class)
                .hasMessage("SecurityErrorCode is null");
    }

}