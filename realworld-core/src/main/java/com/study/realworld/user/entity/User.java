package com.study.realworld.user.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.study.realworld.common.BaseEntity;
import com.study.realworld.follow.entity.Follow;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "USERS")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String password;

    private String bio;

    private String image;

    @OneToMany(mappedBy = "follow")
    private Set<Follow> followings = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Follow> followers = new LinkedHashSet<>();

    @Builder
    public User(final String email, final String username, final String password, final String bio, final String image) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }

    public void setEncodePassword(final String encodedPassword) {
        this.password = encodedPassword;
    }

    public void update(final String email, final String bio, final String image) {
        this.email = email;
        this.bio = bio;
        this.image = image;
    }
}
