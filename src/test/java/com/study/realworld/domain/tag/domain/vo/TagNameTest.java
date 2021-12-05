package com.study.realworld.domain.tag.domain.vo;

import com.study.realworld.domain.tag.domain.vo.TagName;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("태그이름(TagName)")
class TagNameTest {

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
        final TagName tagName = TagName.from(nullOrEmptyString);
        final Set<ConstraintViolation<TagName>> violations = validatorFromFactory.validate(tagName);

        assertThat(violations).isNotEmpty();
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"photo", "willy", "jhon", "kathy"})
    void validation_검증_빈값이_없는_문자열로_객체를_생성할_수_있다(final String tagNameString) {
        final TagName tagName = TagName.from(tagNameString);
        final Set<ConstraintViolation<TagName>> violations = validatorFromFactory.validate(tagName);

        assertThat(violations).isEmpty();
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"photo", "willy", "jhon", "kathy"})
    void 문자열로_객체를_생성할_수_있다(final String tagNameString) {
        final TagName tagName = TagName.from(tagNameString);

        assertAll(
                () -> assertThat(tagName).isNotNull(),
                () -> assertThat(tagName).isExactlyInstanceOf(TagName.class)
        );
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"photo", "willy", "jhon", "kathy"})
    void 값을_반환할_수_있다(final String tagNameString) {
        final TagName tagName = TagName.from(tagNameString);

        assertThat(tagName.tagName()).isEqualTo(tagNameString);
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"photo", "willy", "jhon", "kathy"})
    void 동일_값_기준으로_생성시_동등하다(final String tagNameString) {
        final TagName tagName = TagName.from(tagNameString);
        final TagName other = TagName.from(tagNameString);

        assertAll(
                () -> assertThat(tagName).isEqualTo(other),
                () -> assertThat(tagName).hasSameHashCodeAs(other)
        );
    }
}
