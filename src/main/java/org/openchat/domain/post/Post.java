package org.openchat.domain.post;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {

    private final String postId;
    private final String userId;
    private final String text;
    private final LocalDateTime dateTime;

    public Post(String postId, String userId, String text, LocalDateTime dateTime) {
        this.postId = postId;
        this.userId = userId;
        this.text = text;
        this.dateTime = dateTime;
    }

    public String getPostId() {
        return postId;
    }

    public String getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(postId, post.postId) &&
                Objects.equals(userId, post.userId) &&
                Objects.equals(text, post.text) &&
                Objects.equals(dateTime, post.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, userId, text, dateTime);
    }
}
