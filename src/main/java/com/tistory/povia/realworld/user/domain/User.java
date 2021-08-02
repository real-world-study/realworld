package com.tistory.povia.realworld.user.domain;

import com.tistory.povia.realworld.common.domain.BaseTimeEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Entity(name = "user")
@Where(clause = "deleted_at is null")
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

  protected User(){}

  private User(Email email, String username, String password, String bio, String image) {
    checkArgument(StringUtils.isNotBlank(password), "password should be provided");
    checkUsername(username);
    checkPassword(password);
    checkImageUrl(image);

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

  public String bio() {
    return bio;
  }

  public String image() {
    return image;
  }

  public void initId(Long id){
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id) &&
      email.equals(user.email) &&
      username.equals(user.username) &&
      password.equals(user.password) &&
      Objects.equals(bio, user.bio) &&
      Objects.equals(image, user.image);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, username, password, bio, image);
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

  private static void checkPassword(String password){
    checkArgument(StringUtils.isNotBlank(password), "password should be provided");
    checkArgument(password.length() >= 1 && password.length() <= 25, "username should be 1 to 25 characters");
  }

  private static void checkImageUrl(String image){
    checkArgument(image == null || image.length() <= 255, "Image url length should be 1 to 255 characters");
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
