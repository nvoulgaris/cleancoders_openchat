package org.openchat.domain.user;

import java.util.Objects;

public class Following {

    private final String followerId;
    private final String followeeId;

    public Following(String followerId, String followeeId) {
        this.followerId = followerId;
        this.followeeId = followeeId;
    }

    public String getFollowerId() {
        return followerId;
    }

    public String getFolloweeId() {
        return followeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Following following = (Following) o;
        return Objects.equals(followerId, following.followerId) &&
                Objects.equals(followeeId, following.followeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId, followeeId);
    }
}
