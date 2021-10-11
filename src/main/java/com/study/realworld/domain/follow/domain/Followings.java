package com.study.realworld.domain.follow.domain;

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

    public Followings(final Set<Follow> followings) {
        this.followings = followings;
    }

    public void add(final Follow following) {
        validateArgumentNull(following);
        followings.add(following);
    }

    private void validateArgumentNull(final Follow following) {
        if(Objects.isNull(following)) {
            throw new IllegalArgumentException();
        }
    }

    public Set<Follow> getFollowings() {
        return followings;
    }

}
