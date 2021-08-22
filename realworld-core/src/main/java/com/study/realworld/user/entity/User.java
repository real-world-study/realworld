package com.study.realworld.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.study.realworld.common.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "bio")
    private String bio;

    @Column(name = "image")
    private String image;

    @Column(name = "activated")
    private boolean activated = true;

    @Enumerated(EnumType.STRING)
    private Authority authority = Authority.ROLE_USER;

    public boolean matchPassword(final PasswordEncoder passwordEncoder, final String rawPassword) {
        return passwordEncoder.matches(rawPassword, password);
    }

    public enum Authority{
        ROLE_USER, ROLE_ADMIN
    }

    @Builder
    public User(final String email, final String username, final String password, final String bio, final String image) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }

    public void update(final String email, final String bio, final String image) {
        this.email = email;
        this.bio = bio;
        this.image = image;
    }

    public void delete() {
        activated = false;
        setDeleteAt(LocalDateTime.now());
    }

    public UsernamePasswordAuthenticationToken toAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

    public User encrypt(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
        return this;
    }
}
