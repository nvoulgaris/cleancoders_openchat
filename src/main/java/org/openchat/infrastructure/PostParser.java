package org.openchat.infrastructure;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.post.Post;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

public class PostParser {

    private static DateTimeFormatter formatter = ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static String jsonWith(List<Post> posts) {
        JsonArray json = new JsonArray();
        posts.forEach(post -> json.add(jsonObjectWith(post)));
        return json.toString();
    }

    public static String jsonWith(Post post) {
        return jsonObjectWith(post).toString();
    }

    private static JsonObject jsonObjectWith(Post post) {
        return new JsonObject()
                .add("postId", post.getPostId())
                .add("userId", post.getUserId())
                .add("text", post.getText())
                .add("dateTime", formatter.format(post.getDateTime()));
    }
}
