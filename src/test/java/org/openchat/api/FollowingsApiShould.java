package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openchat.domain.user.Following;
import org.openchat.domain.user.FollowingAlreadyExistsException;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserService;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class FollowingsApiShould {

    private static final String FOLLOWER_ID = UUID.randomUUID().toString();
    private static final String FOLLOWEE_ID = UUID.randomUUID().toString();
    private static final String USER_ID = UUID.randomUUID().toString();
    private static final String USERNAME = "a username";
    private static final String PASSWORD = "a password";
    private static final String ABOUT = "about me";

    private FollowingsApi followingsApi;
    private Following following;
    private User followee;
    private List<User> followees;

    @Mock
    private Request request;
    @Mock
    private Response response;
    @Mock
    private UserService userService;

    @Before
    public void setup() {
        initMocks(this);
        initMocksBehavior();
        followingsApi = new FollowingsApi(userService);
    }

    @Test
    public void returnAnErrorIfFollowingAlreadyExists() {
        doThrow(new FollowingAlreadyExistsException()).when(userService).createFollowing(following);

        String result = followingsApi.create(request, response);

        verify(response).status(400);
        assertThat(result).isEqualTo("Following already exist.");
    }

    @Test
    public void createANewFollowing() {
        followingsApi.create(request, response);

        verify(userService).createFollowing(following);
    }

    @Test
    public void informWhenCreatingANewFollowing() {
        String result = followingsApi.create(request, response);

        verify(response).status(201);
        assertThat(result).isEqualTo("Following created.");
    }

    @Test
    public void getFolloweesForAUser() {
        when(userService.followeesFor(FOLLOWER_ID)).thenReturn(followees);

        String result = followingsApi.getFollowees(request, response);

        verify(response).status(200);
        verify(response).type("application/json");
        assertThat(result).isEqualTo(jsonWith(followees));
    }

    private void initMocksBehavior() {
        following = new Following(FOLLOWER_ID, FOLLOWEE_ID);
        followee = new User(USER_ID, USERNAME, PASSWORD, ABOUT);
        followees = asList(followee);
        when(request.body()).thenReturn(jsonWith(following));
        when(request.params("followerId")).thenReturn(FOLLOWER_ID);
    }

    private String jsonWith(List<User> followees) {
        JsonArray json = new JsonArray();
        followees.forEach(followee -> json.add(jsonObjectWith(followee)));
        return json.toString();
    }

    private String jsonWith(Following following) {
        return jsonObjectWith(following).toString();
    }

    private JsonObject jsonObjectWith(Following following) {
        return new JsonObject()
                .add("followerId", following.getFollowerId())
                .add("followeeId", following.getFolloweeId());
    }

    private JsonObject jsonObjectWith(User user) {
        return new JsonObject()
                .add("id", user.getId())
                .add("username", user.getUsername())
                .add("about", user.getAbout());
    }
}
