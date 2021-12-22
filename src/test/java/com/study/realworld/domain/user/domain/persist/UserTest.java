package com.study.realworld.domain.user.domain.persist;

import com.study.realworld.domain.user.domain.vo.UserBio;
import com.study.realworld.domain.user.domain.vo.UserEmail;
import com.study.realworld.domain.user.domain.vo.UserImage;
import com.study.realworld.domain.user.util.TestPasswordEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("사용자(User)")
public class UserTest {

    @Test
    void 아이덴티티가_같다면_동일한_객체이다() {
        final User user = new User();
        final User other = new User();

        ReflectionTestUtils.setField(user, "userId", 1L);
        ReflectionTestUtils.setField(other, "userId", 1L);

        assertThat(user).isEqualTo(other);
    }

    @Test
    void 패스워드를_인코딩_할_수_있다() {
        final PasswordEncoder passwordEncoder = TestPasswordEncoder.initialize();
        final User user = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE).encode(passwordEncoder);

        assertAll(
                () -> assertThat(user.userPassword()).isNotEqualTo(USER_PASSWORD),
                () -> assertThat(passwordEncoder.matches(USER_PASSWORD.userPassword(), user.userPassword().userPassword())).isTrue()
        );
    }

    @Test
    void 패스워드가_동일하다면_로그인_할_수_있다() {
        final PasswordEncoder passwordEncoder = TestPasswordEncoder.initialize();
        final User user = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE)
                .encode(passwordEncoder);

        assertThat(user.login(USER_PASSWORD, passwordEncoder)).isEqualTo(user);
    }

    @Test
    void 엔티티의_값들은_수정할_수_있다() {
        final UserEmail changedEmail = UserEmail.from("changedEmail");
        final UserBio changedBio = UserBio.from("changedBio");
        final UserImage changedImage = UserImage.from("changedImage");

        final User user = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE)
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
        final User user = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);

        assertAll(
                () -> assertThat(user.userEmail()).isEqualTo(USER_EMAIL),
                () -> assertThat(user.userName()).isEqualTo(USER_NAME),
                () -> assertThat(user.userPassword()).isEqualTo(USER_PASSWORD),
                () -> assertThat(user.userBio()).isEqualTo(USER_BIO),
                () -> assertThat(user.userImage()).isEqualTo(USER_IMAGE)
        );
    }
}
