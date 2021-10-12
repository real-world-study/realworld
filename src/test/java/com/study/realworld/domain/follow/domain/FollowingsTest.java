package com.study.realworld.domain.follow.domain;

import com.study.realworld.domain.follow.error.exception.FollowNullPointerException;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.follow.domain.FollowTest.followBuilder;
import static com.study.realworld.domain.user.domain.persist.UserTest.userBuilder;
import static com.study.realworld.domain.user.domain.vo.BioTest.BIO;
import static com.study.realworld.domain.user.domain.vo.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.vo.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.vo.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.vo.PasswordTest.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class FollowingsTest {

    @DisplayName("Following 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final Followings followings = new Followings();

        assertAll(
                () -> assertThat(followings).isNotNull(),
                () -> assertThat(followings).isExactlyInstanceOf(Followings.class)
        );
    }

    @DisplayName("Following 인스턴스 add 테스트")
    @Test
    void add_test() {
        final Followings followings = new Followings();
        final User user1 = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        final User user2 = userBuilder(new Email("email1"), new Name("username1"), new Password("password1"), new Bio("bio1"), new Image("image1"));
        final Follow follow = followBuilder(user1, user2);
        followings.addFollowing(follow);

        assertThat(followings.getFollowings().size()).isEqualTo(1);
    }

    @DisplayName("Following 인스턴스 add()에 null 입력시 실패 테스트")
    @Test
    void add_fail_test() {
        final Followings followings = new Followings();
        assertThatThrownBy(() -> followings.addFollowing(null))
                .isInstanceOf(FollowNullPointerException.class)
                .hasMessage("Follow 가 null 입니다.");
    }

}