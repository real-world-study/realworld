package com.study.realworld.user.domain.vo;

import com.study.realworld.follow.domain.Follow;
import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.user.domain.User;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Embeddable
public class Follows {

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Follow> follows = new HashSet<>();

    protected Follows() {
    }

    private Follows(Set<Follow> follows) {
        this.follows = follows;
    }

    public static Follows of(Set<Follow> follows) {
        return new Follows(follows);
    }

    public Set<User> followees() {
        return follows.stream()
            .map(Follow::followee)
            .collect(Collectors.toSet());
    }

    public boolean following(Follow follow) {
        checkIsFollow(follow);

        return follows.add(follow);
    }

    private void checkIsFollow(Follow follow) {
        if (isFollow(follow)) {
            throw new BusinessException(ErrorCode.INVALID_FOLLOW);
        }
    }

    public boolean unfollowing(Follow follow) {
        checkIsUnfollow(follow);

        return !follows.remove(follow);
    }

    private void checkIsUnfollow(Follow follow) {
        if (!isFollow(follow)) {
            throw new BusinessException(ErrorCode.INVALID_UNFOLLOW);
        }
    }

    public boolean isFollow(Follow follow) {
        return follows.contains(follow);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Follows followees1 = (Follows) o;
        return Objects.equals(follows, followees1.follows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(follows);
    }

}
