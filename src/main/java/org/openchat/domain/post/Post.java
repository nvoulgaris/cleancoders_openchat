package org.openchat.domain.post;

import java.time.LocalDateTime;

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
}
