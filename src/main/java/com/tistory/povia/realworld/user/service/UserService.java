package com.tistory.povia.realworld.user.service;

import com.tistory.povia.realworld.user.domain.Email;
import com.tistory.povia.realworld.user.domain.User;
import com.tistory.povia.realworld.user.exception.DuplicatedEmailException;
import com.tistory.povia.realworld.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public User join(String username, Email email, String password) {
    checkDuplicatedEmail(email);

    User user = User.builder().email(email).username(username).password(password).build();

    return userRepository.save(user);
  }

  private void checkDuplicatedEmail(Email email){
    userRepository.findByEmail(email).ifPresent(user -> {
      throw new DuplicatedEmailException();
    });
  }
}
