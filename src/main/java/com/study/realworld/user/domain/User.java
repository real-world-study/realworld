package com.study.realworld.user.domain;

import com.study.realworld.article.domain.Article;
import com.study.realworld.articlefavorite.domain.ArticleFavorite;
import com.study.realworld.follow.domain.Follow;
import com.study.realworld.global.domain.BaseTimeEntity;
import com.study.realworld.user.domain.vo.ArticleFavorites;
import com.study.realworld.user.domain.vo.Bio;
import com.study.realworld.user.domain.vo.Email;
import com.study.realworld.user.domain.vo.Follows;
import com.study.realworld.user.domain.vo.Image;
import com.study.realworld.user.domain.vo.Password;
import com.study.realworld.user.domain.vo.Profile;
import com.study.realworld.user.domain.vo.Username;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "user")
@Where(clause = "deleted_at is null")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Profile profile;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private Follows follows = new Follows();

    @Embedded
    private ArticleFavorites articleFavorites = new ArticleFavorites();

    protected User() {
    }

    private User(Long id, Profile profile, Email email, Password password, Follows follows, ArticleFavorites favorites) {
        this.id = id;
        this.profile = profile;
        this.email = email;
        this.password = password;
        this.follows = follows;
        this.articleFavorites = favorites;
    }

    public Long id() {
        return id;
    }

    public Username username() {
        return profile.username();
    }

    public Email email() {
        return email;
    }

    public Password password() {
        return password;
    }

    public Bio bio() {
        return profile.bio();
    }

    public Image image() {
        return profile.image();
    }

    public Set<User> followees() {
        return follows.followees();
    }

    public void changeUsername(Username username) {
        profile.changeUsername(username);
    }

    public void changeEmail(Email email) {
        this.email = email;
    }

    public void changePassword(Password password, PasswordEncoder passwordEncoder) {
        this.password = Password.encode(password, passwordEncoder);
    }

    public void changeBio(Bio bio) {
        profile.changeBio(bio);
    }

    public void changeImage(Image image) {
        profile.changeImage(image);
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = Password.encode(this.password, passwordEncoder);
    }

    public void login(Password rawPassword, PasswordEncoder passwordEncoder) {
        this.password.matchPassword(rawPassword, passwordEncoder);
    }

    public Profile profile() {
        return profile;
    }

    public boolean followUser(User followee) {
        Follow follow = createFollow(followee);
        return follows.following(follow);
    }

    public boolean unfollowUser(User followee) {
        Follow follow = createFollow(followee);
        return follows.unfollowing(follow);
    }

    public boolean isFollow(User followee) {
        Follow follow = createFollow(followee);
        return follows.isFollow(follow);
    }

    private Follow createFollow(User followee) {
        return Follow.builder()
            .follower(this)
            .followee(followee)
            .build();
    }

    public ArticleFavorite createFavoriteForFavoriting(Article article) {
        ArticleFavorite favorite = createArticleFavorite(article);
        return articleFavorites.checkCanFavorite(favorite);
    }

    public ArticleFavorite createFavoriteForUnfavoriting(Article article) {
        ArticleFavorite favorite = createArticleFavorite(article);
        return articleFavorites.checkCanUnfavorite(favorite);
    }

    public boolean isFavoriteArticle(Article article) {
        ArticleFavorite favorite = createArticleFavorite(article);
        return articleFavorites.isFavoriteArticle(favorite);
    }

    private ArticleFavorite createArticleFavorite(Article article) {
        return ArticleFavorite.builder()
            .user(this)
            .article(article)
            .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {

        private Long id;
        private Profile profile;
        private Email email;
        private Password password;
        private Follows follows = new Follows();
        private ArticleFavorites favorites = new ArticleFavorites();

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder profile(Profile profile) {
            this.profile = profile;
            return this;
        }

        public Builder profile(Username username, Bio bio, Image image) {
            this.profile = Profile.Builder()
                .username(username)
                .bio(bio)
                .image(image)
                .build();
            return this;
        }

        public Builder email(Email email) {
            this.email = email;
            return this;
        }

        public Builder password(Password password) {
            this.password = password;
            return this;
        }

        public Builder follows(Follows follows) {
            this.follows = follows;
            return this;
        }

        public Builder articleFavorites(ArticleFavorites favorites) {
            this.favorites = favorites;
            return this;
        }

        public User build() {
            return new User(id, profile, email, password, follows, favorites);
        }
    }

}
