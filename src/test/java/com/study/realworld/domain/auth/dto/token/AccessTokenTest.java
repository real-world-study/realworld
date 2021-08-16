package com.study.realworld.domain.auth.dto.token;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AccessTokenTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @DisplayName("AccessToken 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final AccessToken accessToken = new AccessToken();

        assertAll(
                () -> assertThat(accessToken).isNotNull(),
                () -> assertThat(accessToken).isInstanceOf(AccessToken.class)
        );
    }

    @DisplayName("AccessToken 인스턴스 정적 팩토리 메서드 테스트")
    @Test
    void static_factory_method_test() {
        final String accessTokenString = "accessToken";
        final AccessToken accessToken = new AccessToken(accessTokenString);

        assertAll(
                () -> assertThat(accessToken).isNotNull(),
                () -> assertThat(accessToken).isInstanceOf(AccessToken.class)
        );
    }

    @DisplayName("AccessToken 인스턴스 getter 테스트")
    @Test
    void getter_test() {
        final String accessTokenString = "accessToken";
        final AccessToken accessToken = new AccessToken(accessTokenString);

        assertThat(accessToken.accessToken()).isEqualTo(accessTokenString);
    }

    @DisplayName("AccessToken 인스턴스 @NotBlank 테스트")
    @Test
    void accessToken_notBlank_test() {
        final String accessTokenString = "   ";
        final AccessToken accessToken = new AccessToken(accessTokenString);
        final Set<ConstraintViolation<AccessToken>> violations = validator.validate(accessToken);

        assertAll(
                () -> assertThat(violations.size()).isEqualTo(1),
                () -> assertThat(violations.iterator().next().getMessage()).isEqualTo("AccessToken must have not blank")
        );
    }

}