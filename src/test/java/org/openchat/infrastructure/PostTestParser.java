package org.openchat.infrastructure;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.post.Post;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

public class PostTestParser {

    private static DateTimeFormatter formatter = ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static String POST_ID = "postId";
    private static String USER_ID = "userId";
    private static String TEXT = "text";
    private static String DATE_TIME = "dateTime";

    public static String jsonWith(List<Post> posts) {
        JsonArray json = new JsonArray();
        posts.forEach(post -> json.add(jsonObjectWith(post)));
        return json.toString();
    }

    public static String jsonWith(Post post) {
        return jsonObjectWith(post).toString();
    }

    public static String jsonWith(String text) {
        return new JsonObject()
                .add(TEXT, text)
                .toString();
    }

    private static JsonObject jsonObjectWith(Post post) {
        return new JsonObject()
                .add(POST_ID, post.getPostId())
                .add(USER_ID, post.getUserId())
                .add(TEXT, post.getText())
                .add(DATE_TIME, formatter.format(post.getDateTime()));
    }
}
