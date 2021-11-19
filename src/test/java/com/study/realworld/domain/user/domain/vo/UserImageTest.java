package com.study.realworld.domain.user.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("사용자 이미지(UserImage)")
class UserImageTest {

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"", "photo", "willy", "jhon", "kathy"})
    void 문자열로_객체를_생성할_수_있다(final String userImageString) {
        final UserImage userImage = UserImage.from(userImageString);

        assertAll(
                () -> assertThat(userImage).isNotNull(),
                () -> assertThat(userImage).isExactlyInstanceOf(UserImage.class)
        );
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"", "photo", "willy", "jhon", "kathy"})
    void 값을_반환할_수_있다(final String userImageString) {
        final UserImage userImage = UserImage.from(userImageString);

        assertThat(userImage.value()).isEqualTo(userImageString);
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"", "photo", "willy", "jhon", "kathy"})
    void 동일_값_기준으로_생성시_동등하다(final String userImageString) {
        final UserImage userImage = UserImage.from(userImageString);
        final UserImage other = UserImage.from(userImageString);

        assertAll(
                () -> assertThat(userImage).isEqualTo(other),
                () -> assertThat(userImage).hasSameHashCodeAs(other)
        );
    }
}
