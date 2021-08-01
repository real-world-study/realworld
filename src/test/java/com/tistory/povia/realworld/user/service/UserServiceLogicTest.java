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

  private String username;

  private Email email;

  private String password;

  private MemoryUserRepository memoryUserRepository = new MemoryUserRepository();

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

    User user = userService.join(username, email, password);

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
  void joinFailedTest(){

    userService.join(username, email, password);

    assertThatThrownBy(() -> userService.join(username, email, password)).isInstanceOf(DuplicatedEmailException.class).hasMessage("Duplicated Email found");
  }
}