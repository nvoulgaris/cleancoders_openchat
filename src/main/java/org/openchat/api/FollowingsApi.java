package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.Following;
import org.openchat.domain.user.FollowingAlreadyExistsException;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserService;
import spark.Request;
import spark.Response;

import java.util.List;

import static org.eclipse.jetty.http.HttpStatus.*;
import static org.openchat.infrastructure.UserParser.jsonWith;

public class FollowingsApi {

    private static final String DEFAULT_VALUE = "";

    private UserService userService;

    public FollowingsApi(UserService userService) {
        this.userService = userService;
    }

    public String create(Request request, Response response) {
        Following following = followingFrom(request);

        try {
            userService.createFollowing(following);
            return createdResponse(response);
        } catch (FollowingAlreadyExistsException e) {
            return badRequestResponse(response);
        }
    }

    public String getFollowees(Request request, Response response) {
        String followerId = request.params("followerId");
        List<User> followees = userService.followeesFor(followerId);
        return okResponse(response, followees);
    }

    private String createdResponse(Response response) {
        response.status(CREATED_201);
        return "Following created.";
    }

    private String badRequestResponse(Response response) {
        response.status(BAD_REQUEST_400);
        return "Following already exist.";
    }

    private String okResponse(Response response, List<User> followees) {
        response.status(OK_200);
        response.type("application/json");
        return jsonWith(followees);
    }

    private Following followingFrom(Request request) {
        JsonObject jsonObject = Json.parse(request.body()).asObject();
        String followerId = jsonObject.getString("followerId", DEFAULT_VALUE);
        String followeeId = jsonObject.getString("followeeId", DEFAULT_VALUE);
        return new Following(followerId, followeeId);
    }
}
