package com.study.realworld.domain.user.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("사용자 소개글(UserBio)")
class UserBioTest {

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"", "photo", "willy", "jhon", "kathy"})
    void 문자열로_객체를_생성할_수_있다(final String userBioString) {
        final UserBio userBio = UserBio.from(userBioString);

        assertAll(
                () -> assertThat(userBio).isNotNull(),
                () -> assertThat(userBio).isExactlyInstanceOf(UserBio.class)
        );
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"", "photo", "willy", "jhon", "kathy"})
    void 값을_반환할_수_있다(final String userBioString) {
        final UserBio userBio = UserBio.from(userBioString);

        assertThat(userBio.userBio()).isEqualTo(userBioString);
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"", "photo", "willy", "jhon", "kathy"})
    void 동일_값_기준으로_생성시_동등하다(final String userBioString) {
        final UserBio userBio = UserBio.from(userBioString);
        final UserBio other = UserBio.from(userBioString);

        assertAll(
                () -> assertThat(userBio).isEqualTo(other),
                () -> assertThat(userBio).hasSameHashCodeAs(other)
        );
    }
}
