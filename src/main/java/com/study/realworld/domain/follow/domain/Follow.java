package com.study.realworld.domain.follow.domain;

import com.study.realworld.domain.user.domain.persist.User;

import javax.persistence.*;

@Entity
public class Follow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", nullable = false, updatable = false)
    private User following;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false, updatable = false)
    private User follower;

    protected Follow() {
    }

    public Follow changeFollowing(final User following) {
        this.following = following;
        return this;
    }

    public Follow changeFollower(final User follower) {
        this.follower = follower;
        return this;
    }

    public Long id() {
        return id;
    }

    public User following() {
        return following;
    }

    public User follower() {
        return follower;
    }

    private Follow(final FollowBuilder followBuilder) {
        following = followBuilder.following;
        follower = followBuilder.follower;
    }

    public static FollowBuilder Builder() {
        return new FollowBuilder();
    }

    public static final class FollowBuilder {
        private User following;
        private User follower;

        private FollowBuilder() {
        }

        public FollowBuilder following(final User following) {
            this.following = following;
            return this;
        }

        public FollowBuilder follower(final User follower) {
            this.follower = follower;
            return this;
        }

        public Follow build() {
            return new Follow(this);
        }
    }

}
