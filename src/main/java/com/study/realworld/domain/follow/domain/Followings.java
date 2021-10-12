package com.study.realworld.domain.follow.domain;

import com.study.realworld.domain.follow.error.exception.FollowNullPointerException;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Embeddable
public class Followings {

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> followings = new HashSet<>();

    public Followings() {
    }

    public void addFollowing(final Follow following) {
        validateFollowNull(following);
        followings.add(following);
    }

    private void validateFollowNull(final Follow following) {
        if(Objects.isNull(following)) {
            throw new FollowNullPointerException();
        }
    }

    public Set<Follow> followings() {
        return followings;
    }

}
