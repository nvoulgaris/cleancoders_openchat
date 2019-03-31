package org.openchat.infrastructure;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.post.Post;

import java.util.List;

public class PostParser {

    public static String jsonWith(List<Post> posts) {
        JsonArray json = new JsonArray();
        posts.forEach(post -> json.add(jsonWith(post)));
        return json.toString();
    }

    public static String jsonWith(Post post) {
        return new JsonObject()
                .add("postId", post.getPostId())
                .add("userId", post.getUserId())
                .add("text", post.getText())
                .add("dateTime", post.getDateTime().toString())
                .toString();
    }
}
