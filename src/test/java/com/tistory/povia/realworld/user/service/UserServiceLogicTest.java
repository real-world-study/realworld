package com.tistory.povia.realworld.user.service;

import com.tistory.povia.realworld.user.domain.Email;
import com.tistory.povia.realworld.user.domain.User;
import com.tistory.povia.realworld.user.exception.DuplicatedEmailException;
import com.tistory.povia.realworld.user.repository.MemoryUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceLogicTest {

  private final MemoryUserRepository memoryUserRepository = new MemoryUserRepository();
  private String username;
  private Email email;
  private String password;
  private UserService userService = new UserService(memoryUserRepository);

  @BeforeEach
  void setUp() {
    username = "tester";
    email = new Email("test@gmail.com");
    password = "1234";
  }

  @Test
  @DisplayName("일반적인 방법으로는 join 에 성공한 후 결과를 return 해야 함")
  void joinSuccessTest() {
    User joinUser = User.builder().username(username).email(email).password(password).build();
    User user = userService.join(joinUser);

    Assertions.assertAll(
      () -> assertThat(user).isNotNull(),
      () -> assertThat(user.id()).isNotNull(),
      () -> assertThat(user.username()).isEqualTo(username),
      () -> assertThat(user.email()).isEqualTo(email),
      () -> assertThat(user.password()).isNotNull()
    );

  }

  @Test
  @DisplayName("중복된 이메일로는 가입 불가능")
  void joinFailedTest() {
    User duplicateTestUser = User.builder().username(username).email(email).password(password).build();
    userService.join(duplicateTestUser);

    assertThatThrownBy(() -> userService.join(duplicateTestUser)).isInstanceOf(DuplicatedEmailException.class).hasMessage("Duplicated Email found");
  }
}