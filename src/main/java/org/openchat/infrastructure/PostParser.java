package org.openchat.infrastructure;

import com.eclipsesource.json.JsonObject;
import org.openchat.domain.post.Post;

public class PostParser {

    public static String jsonWith(Post post) {
        return new JsonObject()
                .add("postId", post.getPostId())
                .add("userId", post.getUserId())
                .add("text", post.getText())
                .add("dateTime", post.getDateTime().toString())
                .toString();
    }
}
