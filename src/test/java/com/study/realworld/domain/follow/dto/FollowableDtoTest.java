package com.study.realworld.domain.follow.dto;

import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.user.domain.persist.UserTest.userBuilder;
import static com.study.realworld.domain.user.domain.vo.BioTest.BIO;
import static com.study.realworld.domain.user.domain.vo.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.vo.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.vo.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.vo.PasswordTest.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FollowableDtoTest {

    @DisplayName("FollowableDto 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final FollowableDto followableDto = new FollowableDto();

        assertAll(
                () -> assertThat(followableDto).isNotNull(),
                () -> assertThat(followableDto).isExactlyInstanceOf(FollowableDto.class)
        );
    }

    @DisplayName("FollowableDto 정적 팩토리 메서드 fromUserAndFollowable() 테스트")
    @Test
    void fromUserAndFollowable_test() {
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        final boolean followable = false;
        final FollowableDto followableDto = FollowableDto.fromUserAndFollowable(user, followable);

        assertAll(
                () -> assertThat(followableDto).isNotNull(),
                () -> assertThat(followableDto).isExactlyInstanceOf(FollowableDto.class)
        );
    }

}