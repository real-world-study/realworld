package com.tistory.povia.realworld.auth.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

  private final AuthenticationManager authenticationManager;

  public AuthController(AuthenticationManager authenticationManager){
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/api/users/login")
  public AuthResponse login(@RequestBody AuthRequest authRequest){

    return null;
  }
}
