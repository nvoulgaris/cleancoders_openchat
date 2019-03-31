package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.post.InappropriateLanguageException;
import org.openchat.domain.post.Post;
import org.openchat.domain.post.PostService;
import spark.Request;
import spark.Response;

import java.util.List;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.CREATED_201;
import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.openchat.infrastructure.PostParser.jsonWith;

public class PostsApi {

    private static final String DEFAULT_VALUE = "";

    private PostService postService;

    public PostsApi(PostService postService) {
        this.postService = postService;
    }

    public String create(Request request, Response response) {
        String userId = request.params("userId");
        String text = textFrom(request);

        try {
            Post post = postService.createPost(userId, text);
            return createdResponse(response, post);
        } catch (InappropriateLanguageException e) {
            return badRequestResponse(response);
        }
    }

    public String postsByUser(Request request, Response response) {
        String userId = request.params("userId");
        List<Post> posts = postService.postsBy(userId);
        response.status(OK_200);
        response.type("application/json");
        return jsonWith(posts);
    }

    private String createdResponse(Response response, Post post) {
        response.status(CREATED_201);
        response.type("application/json");
        return jsonWith(post);
    }

    private String badRequestResponse(Response response) {
        response.status(BAD_REQUEST_400);
        return "Post contains inappropriate language.";
    }

    private String textFrom(Request request) {
        JsonObject json = Json.parse(request.body()).asObject();
        return json.getString("text", DEFAULT_VALUE);
    }
}
