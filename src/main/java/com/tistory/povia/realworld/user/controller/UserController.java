package com.tistory.povia.realworld.user.controller;

import com.tistory.povia.realworld.user.domain.Email;
import com.tistory.povia.realworld.user.domain.User;
import com.tistory.povia.realworld.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("api/users")
  public ResponseEntity<?> join(@ModelAttribute JoinRequest joinRequest) {
    User user = userService.join(joinRequest.username(), new Email(joinRequest.address()), joinRequest.password());

    return new ResponseEntity<>(user, HttpStatus.OK);
  }
}
