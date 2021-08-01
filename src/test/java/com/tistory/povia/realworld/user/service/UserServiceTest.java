package com.tistory.povia.realworld.user.service;

import com.tistory.povia.realworld.user.domain.Email;
import com.tistory.povia.realworld.user.domain.User;
import com.tistory.povia.realworld.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

class UserServiceTest {

  private String username;

  private Email email;

  private String password;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  @Autowired
  private UserService userService;

  @BeforeEach
  void setUp() {
    username = "tester";
    email = new Email("test@gmail.com");
    password = "1234";
  }

  @Test
  @DisplayName("일반적인 방법으로는 join에 성공한 후 결과를 return해야 함")
  void joinSuccessTest(){

    User user = userService.registration(username, email, password);

    assertThat(user).isNotNull();
    assertThat(user.id()).isNotNull();
    assertThat(user.email()).isEqualTo(email);
  }
}