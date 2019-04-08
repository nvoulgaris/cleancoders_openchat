package org.openchat.api;

import org.openchat.domain.post.Post;
import org.openchat.domain.post.WallService;
import spark.Request;
import spark.Response;

import java.util.List;

import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.openchat.infrastructure.PostParser.jsonWith;

public class WallApi {

    private WallService wallService;

    public WallApi(WallService wallService) {
        this.wallService = wallService;
    }

    public String wallForUser(Request request, Response response) {
        String userId = request.params("userId");
        List<Post> wallPosts = wallService.wallFor(userId);

        response.status(OK_200);
        response.type("application/json");
        return jsonWith(wallPosts);
    }
}
