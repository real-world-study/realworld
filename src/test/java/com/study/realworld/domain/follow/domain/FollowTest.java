package com.study.realworld.domain.follow.domain;

import com.study.realworld.domain.user.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.user.domain.BioTest.BIO;
import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.UserTest.userBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class FollowTest {

    @DisplayName("Follow 인스턴스 기본 생성자 테스트")
    @Test
    void constructor_test() {
        final Follow follow = new Follow();

        assertAll(
                () -> assertThat(follow).isNotNull(),
                () -> assertThat(follow).isExactlyInstanceOf(Follow.class)
        );
    }

    @DisplayName("Follow 인스턴스 빌더 테스트")
    @Test
    void builder_test() {
        final User following = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        final User follower = userBuilder(new Email("Email2@email.com"), new Name("differentUserName"), new Password("Password2"), new Bio("Bio2"), new Image("Image2"));

        final Follow follow = followBuilder(following, follower);

        assertAll(
                () -> assertThat(follow).isNotNull(),
                () -> assertThat(follow).isExactlyInstanceOf(Follow.class)
        );
    }

    @DisplayName("Follow 인스턴스 getter 테스트")
    @Test
    void getter_test() {
        final User following = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        final User follower = userBuilder(new Email("Email2@email.com"), new Name("differentUserName"), new Password("Password2"), new Bio("Bio2"), new Image("Image2"));

        final Follow follow = followBuilder(following, follower);

        assertAll(
                () -> assertThat(follow.following()).isEqualTo(following),
                () -> assertThat(follow.follower()).isEqualTo(follower)
        );
    }

    @DisplayName("Follow 인스턴스 change 테스트")
    @Test
    void change_test() {
        final User following = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        final User follower = userBuilder(new Email("Email2@email.com"), new Name("differentUserName"), new Password("Password2"), new Bio("Bio2"), new Image("Image2"));
        final User changeFollowing = userBuilder(new Email("change@email.com"), new Name("changeName"), new Password("changePassword"), new Bio("Change"), new Image("/change"));
        final User changeFollower = userBuilder(new Email("change2@email.com"), new Name("changeName2"), new Password("changePassword2"), new Bio("Change2"), new Image("/change2"));

        final Follow follow = followBuilder(following, follower)
                .changeFollowing(changeFollowing)
                .changeFollower(changeFollower);

        assertAll(
                () -> assertThat(follow.following()).isEqualTo(changeFollowing),
                () -> assertThat(follow.follower()).isEqualTo(changeFollower)
        );
    }

    public static final Follow followBuilder(final User following, final User follower) {
        return Follow.Builder()
                .following(following)
                .follower(follower)
                .build();
    }

}