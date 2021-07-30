package com.tistory.povia.realworld.user.domain;

import com.tistory.povia.realworld.common.domain.BaseTimeEntity;

import javax.persistence.Entity;

@Entity
public class User extends BaseTimeEntity {

  private Long id;

  private String username;

  private String password;

  
}
