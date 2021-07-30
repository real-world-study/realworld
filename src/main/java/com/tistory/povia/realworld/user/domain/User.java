package com.tistory.povia.realworld.user.domain;

import com.tistory.povia.realworld.common.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends BaseTimeEntity {

    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public User(Long id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
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

    public String changeEmail() {}

    public void changePassword(String password) {
        this.password = password;
    }

    static class Builder {
        private Long id;
        private String email;
        private String username;
        private String password;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
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

        public User build() {
            return new User(id, email, username, password);
        }
    }
}
