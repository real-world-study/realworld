package com.tistory.povia.realworld.user.domain;

import com.tistory.povia.realworld.common.domain.BaseTimeEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;

@Entity(name = "user")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Embedded
  private Email email;

  @Column(name = "username", length = 50, nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "bio")
  private String bio;

  @Column(name = "image")
  private String image;

  private User(Email email, String username, String password, String bio, String image) {
    checkArgument(StringUtils.isNotBlank(password), "password should be provided");
    checkUsername(username);

    this.email = email;
    this.username = username;
    this.password = password;
    this.bio = bio;
    this.image = image;
  }

  public Long id() {
    return id;
  }

  public String username() {
    return username;
  }

  public String password() {
    return password;
  }

  public Email email() {
    return email;
  }

  public void initId(Long id){
    this.id = id;
  }
  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", email=" + email +
      ", username='" + username + '\'' +
      ", password='" + password + '\'' +
      ", bio='" + bio + '\'' +
      ", image='" + image + '\'' +
      '}';
  }

  private static void checkUsername(String username) {
    checkArgument(StringUtils.isNotBlank(username), "username should be provided");
    checkArgument(username.length() >= 1 && username.length() <= 25, "username should be between 1 to 25 characters");
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Email email;
    private String username;
    private String password;
    private String bio;
    private String image;

    public Builder email(Email email) {
      this.email = email;
      return this;
    }

    public Builder username(String username) {
      this.username = username;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder bio(String bio) {
      this.bio = bio;
      return this;
    }

    public Builder image(String image) {
      this.image = image;
      return this;
    }

    public User build() {
      return new User(email, username, password, bio, image);
    }
  }
}
