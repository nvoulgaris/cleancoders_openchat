package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.Following;
import org.openchat.domain.user.FollowingAlreadyExistsException;
import org.openchat.domain.user.UserService;
import spark.Request;
import spark.Response;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.CREATED_201;

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
            response.status(CREATED_201);
            return "Following created.";
        } catch (FollowingAlreadyExistsException e) {
            response.status(BAD_REQUEST_400);
            return "Following already exist.";
        }
    }

    private Following followingFrom(Request request) {
        JsonObject jsonObject = Json.parse(request.body()).asObject();
        String followerId = jsonObject.getString("followerId", DEFAULT_VALUE);
        String followeeId = jsonObject.getString("followeeId", DEFAULT_VALUE);
        return new Following(followerId, followeeId);
    }
}
