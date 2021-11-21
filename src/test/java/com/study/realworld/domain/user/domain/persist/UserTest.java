package com.study.realworld.domain.user.domain.persist;

import com.study.realworld.domain.user.domain.vo.*;
import com.study.realworld.domain.user.domain.vo.util.TestPasswordEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.study.realworld.domain.user.domain.vo.util.UserVOFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("사용자(User)")
public class UserTest {

    @Test
    void 빌더를_통해_객체를_생성할_수_있다() {
        final User user = User.builder()
                .userEmail(USER_EMAIL)
                .userName(USER_NAME)
                .userPassword(USER_PASSWORD)
                .userBio(USER_BIO)
                .userImage(USER_IMAGE)
                .build();

        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user).isExactlyInstanceOf(User.class)
        );
    }

    @Test
    void 패스워드를_인코딩_할_수_있다() {
        final PasswordEncoder passwordEncoder = TestPasswordEncoder.initialize();
        final User user = testUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE)
                .encode(passwordEncoder);

        assertAll(
                () -> assertThat(user.userPassword()).isNotEqualTo(USER_PASSWORD),
                () -> assertThat(passwordEncoder.matches(USER_PASSWORD.value(), user.userPassword().value())).isTrue()
        );
    }

    @Test
    void 패스워드가_동일하다면_로그인_할_수_있다() {
        final PasswordEncoder passwordEncoder = TestPasswordEncoder.initialize();
        final User user = testUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE)
                .encode(passwordEncoder);

        assertThat(user.login(USER_PASSWORD, passwordEncoder)).isEqualTo(user);
    }

    @Test
    void 엔티티의_값들은_수정할_수_있다() {
        final UserEmail changedEmail = UserEmail.from("changedEmail");
        final UserBio changedBio = UserBio.from("changedBio");
        final UserImage changedImage = UserImage.from("changedImage");

        final User user = testUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE)
                .changeEmail(changedEmail)
                .changeBio(changedBio)
                .changeImage(changedImage);

        assertAll(
                () -> assertThat(user.userEmail()).isEqualTo(changedEmail),
                () -> assertThat(user.userBio()).isEqualTo(changedBio),
                () -> assertThat(user.userImage()).isEqualTo(changedImage)
        );
    }

    @Test
    void 엔티티의_값들은_반환할_수_있다() {
        final User user = testUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);

        assertAll(
                () -> assertThat(user.userEmail()).isEqualTo(USER_EMAIL),
                () -> assertThat(user.userName()).isEqualTo(USER_NAME),
                () -> assertThat(user.userPassword()).isEqualTo(USER_PASSWORD),
                () -> assertThat(user.userBio()).isEqualTo(USER_BIO),
                () -> assertThat(user.userImage()).isEqualTo(USER_IMAGE)
        );
    }

    public static User testUser(final String userEmail, final String userName,
                         final String userPassword, final String userBio, final String userImage) {
        return testUser(
                UserEmail.from(userEmail), UserName.from(userName),
                UserPassword.encode(userPassword, TestPasswordEncoder.initialize()),
                UserBio.from(userBio), UserImage.from(userImage)
        );
    }

    public static User testUser(final UserEmail userEmail, final UserName userName,
                         final UserPassword userPassword, final UserBio userBio, final UserImage userImage) {
        return User.builder()
                .userEmail(userEmail)
                .userName(userName)
                .userPassword(userPassword)
                .userBio(userBio)
                .userImage(userImage)
                .build();
    }

}