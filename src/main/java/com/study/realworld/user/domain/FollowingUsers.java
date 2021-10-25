package com.study.realworld.user.domain;

import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Embeddable
public class FollowingUsers {

    @JoinTable(name = "follow",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "follower_id"))
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> followingUsers = new HashSet<>();

    protected FollowingUsers() {
    }

    private FollowingUsers(Set<User> followingUsers) {
        this.followingUsers = followingUsers;
    }

    public static FollowingUsers of(Set<User> followingUsers) {
        return new FollowingUsers(followingUsers);
    }

    public boolean isFollow(User user) {
        return followingUsers.contains(user);
    }

    public FollowingUsers followingUser(User user) {
        checkFollowingUser(user);

        followingUsers.add(user);
        return this;
    }

    private void checkFollowingUser(User user) {
        if (followingUsers.contains(user)) {
            throw new BusinessException(ErrorCode.INVALID_FOLLOW);
        }
    }

    public FollowingUsers unfollowingUser(User user) {
        checkUnfollowingUser(user);

        followingUsers.remove(user);
        return this;
    }

    private void checkUnfollowingUser(User user) {
        if (!followingUsers.contains(user)) {
            throw new BusinessException(ErrorCode.INVALID_UNFOLLOW);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FollowingUsers that = (FollowingUsers) o;
        return Objects.equals(followingUsers, that.followingUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followingUsers);
    }

}
