package com.tistory.povia.realworld.user.service;

import com.tistory.povia.realworld.user.domain.Email;
import com.tistory.povia.realworld.user.domain.User;
import com.tistory.povia.realworld.user.exception.DuplicatedEmailException;
import com.tistory.povia.realworld.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

  @Autowired
  private UserService userService;

  private String username;

  private Email email;

  private String password;

  @BeforeEach
  void setUp() {
    username = "tester";
    email = new Email("test@gmail.com");
    password = "1234";
  }

  @Test
  @DisplayName("일반적인 방법으로는 join에 성공한 후 결과를 return해야 함")
  void joinSuccessTest() {

    User user = userService.join(username, email, password);

    assertThat(user).isNotNull();
    assertThat(user.id()).isNotNull();
    assertThat(user.email()).isEqualTo(email);
  }

  @Test
  @DisplayName("중복된 이메일로는 가입 불가능")
  void joinFailedTest(){

    userService.join(username, email, password);

    assertThatThrownBy(() ->
      userService.join(username, email, password)
    ).isInstanceOf(DuplicatedEmailException.class)
      .hasMessage("Duplicated Email found");
  }
}
