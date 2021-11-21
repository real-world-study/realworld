package com.study.realworld.domain.follow.domain;

import com.study.realworld.domain.user.domain.persist.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee", nullable = false, foreignKey = @ForeignKey(name = "fk_follow_to_followee"))
    private User followee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower", nullable = false, foreignKey = @ForeignKey(name = "fk_follow_to_follower"))
    private User follower;

    @Builder
    public Follow(final User followee, final User follower) {
        this.followee = followee;
        this.follower = follower;
    }

    public Long id() {
        return id;
    }

    public User followee() {
        return followee;
    }

    public User follower() {
        return follower;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Follow follow = (Follow) o;
        return Objects.equals(id(), follow.id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id());
    }
}
