package com.study.realworld.domain.user.domain.vo;

import com.study.realworld.domain.user.domain.vo.util.TestPasswordEncoder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("사용자 비밀번호(UserPassword)")
class UserPasswordTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validatorFromFactory;

    @BeforeAll
    public static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validatorFromFactory = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @NullAndEmptySource
    void validation_널_또는_빈값_문자열로_객체를_생성할_수_없다(final String nullOrEmptyString) {
        final UserPassword userPassword = new UserPassword();
        ReflectionTestUtils.setField(userPassword, "userPassword", nullOrEmptyString);
        final Set<ConstraintViolation<UserPassword>> violations = validatorFromFactory.validate(userPassword);

        assertThat(violations).isNotEmpty();
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"photo", "willy", "jhon", "kathy"})
    void validation_검증_빈값이_없는_문자열로_인코딩을_할_수_있다(final String userPasswordString) {
        final UserPassword userPassword = new UserPassword();
        ReflectionTestUtils.setField(userPassword, "userPassword", userPasswordString);
        final Set<ConstraintViolation<UserPassword>> violations = validatorFromFactory.validate(userPassword);

        assertThat(violations).isEmpty();
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"photo", "willy", "jhon", "kathy"})
    void 문자열로_객체를_생성할_수_있다(final String userPasswordString) {
        final PasswordEncoder passwordEncoder =  TestPasswordEncoder.initialize();
        final UserPassword userPassword = UserPassword.encode(userPasswordString, passwordEncoder);

        assertAll(
                () -> assertThat(userPassword).isNotNull(),
                () -> assertThat(userPassword).isExactlyInstanceOf(UserPassword.class)
        );
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"photo", "willy", "jhon", "kathy"})
    void 값을_반환할_수_있다(final String userPasswordString) {
        final PasswordEncoder passwordEncoder = TestPasswordEncoder.initialize();
        final UserPassword userPassword = UserPassword.encode(userPasswordString, passwordEncoder);

        assertThat(passwordEncoder.matches(userPasswordString, userPassword.value())).isTrue();
    }
}

