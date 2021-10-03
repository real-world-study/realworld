package com.study.realworld.domain.follow.domain;

import com.study.realworld.domain.user.domain.User;

public class Follow {

    private User following;
    private User follower;

    protected Follow() {
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
