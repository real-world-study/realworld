package com.study.realworld.follow.domain;

import com.study.realworld.user.domain.User;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "follow")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "follower_id", nullable = false, foreignKey = @ForeignKey(name = "fk_follow_to_follower_id"))
    @ManyToOne(fetch = FetchType.LAZY)
    private User follower;

    @JoinColumn(name = "followee_id", nullable = false, foreignKey = @ForeignKey(name = "fk_follow_to_followee_id"))
    @ManyToOne(fetch = FetchType.LAZY)
    private User followee;

    protected Follow() {
    }

    private Follow(Long id, User follower, User followee) {
        this.id = id;
        this.follower = follower;
        this.followee = followee;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long id;
        private User follower;
        private User followee;

        private Builder() {
        }

        public Builder follower(User follower) {
            this.follower = follower;
            return this;
        }

        public Builder followee(User followee) {
            this.followee = followee;
            return this;
        }

        public Follow build() {
            return new Follow(id, follower, followee);
        }

    }

}
