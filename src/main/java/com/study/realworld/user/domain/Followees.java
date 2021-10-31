package com.study.realworld.user.domain;

import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Embeddable
public class Followees {

    @OneToMany(mappedBy = "followee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> followees = new HashSet<>();

    protected Followees() {
    }

    private Followees(Set<User> followees) {
        this.followees = followees;
    }

    public static Followees of(Set<User> followees) {
        return new Followees(followees);
    }

    public boolean isFollow(User user) {
        return followees.contains(user);
    }

    public void checkIsFollowingUser(User user) {
        if (isFollow(user)) {
            throw new BusinessException(ErrorCode.INVALID_FOLLOW);
        }
    }

    public void checkIsUnfollowingUser(User user) {
        if (!isFollow(user)) {
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
        Followees that = (Followees) o;
        return Objects.equals(followees, that.followees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followees);
    }

}
