package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openchat.domain.user.Following;
import org.openchat.domain.user.FollowingAlreadyExistsException;
import org.openchat.domain.user.UserService;
import spark.Request;
import spark.Response;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class FollowingsApiShould {

    private static final String FOLLOWER_ID = UUID.randomUUID().toString();
    private static final String FOLLOWEE_ID = UUID.randomUUID().toString();

    private FollowingsApi followingsApi;
    private Following following;

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

    private void initMocksBehavior() {
        following = new Following(FOLLOWER_ID, FOLLOWEE_ID);
        when(request.body()).thenReturn(jsonWith(following));
    }

    private String jsonWith(Following following) {
        return new JsonObject()
                .add("followerId", following.getFollowerId())
                .add("followeeId", following.getFolloweeId())
                .toString();
    }
}
