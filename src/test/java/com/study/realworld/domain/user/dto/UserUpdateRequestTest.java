package com.study.realworld.domain.user.dto;

import com.study.realworld.domain.user.domain.Bio;
import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.user.domain.BioTest.BIO;
import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.ImageTest.IMAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserUpdateRequestTest {

    @DisplayName("UserUpdateRequest 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final UserUpdateRequest userUpdateRequest = new UserUpdateRequest();

        assertAll(
                () -> assertThat(userUpdateRequest).isNotNull(),
                () -> assertThat(userUpdateRequest).isExactlyInstanceOf(UserUpdateRequest.class)
        );
    }

    @DisplayName("UserUpdateRequest 생성자 테스트")
    @Test
    void constructor_test() {
        final UserUpdateRequest userUpdateRequest =
                new UserUpdateRequest(new Email(EMAIL), new Bio(BIO), new Image(IMAGE));

        assertAll(
                () -> assertThat(userUpdateRequest).isNotNull(),
                () -> assertThat(userUpdateRequest).isExactlyInstanceOf(UserUpdateRequest.class)
        );
    }

}