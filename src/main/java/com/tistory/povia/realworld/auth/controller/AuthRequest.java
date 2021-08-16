package com.tistory.povia.realworld.auth.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class AuthRequest {

  @JsonProperty("email")
  private String email;

  @JsonProperty("password")
  private String password;

  public AuthRequest() {
  }

  public AuthRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String email() {
    return email;
  }

  public String password() {
    return password;
  }
}
