package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openchat.domain.post.Post;
import org.openchat.domain.post.WallService;
import spark.Request;
import spark.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class WallApiShould {

    private static DateTimeFormatter formatter = ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final String POST_ID = UUID.randomUUID().toString();
    private static final String USER_ID = UUID.randomUUID().toString();
    private static final String TEXT = "Hello";
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2019, 3, 30, 22, 30, 0);

    private WallApi wallApi;
    private List<Post> wallPosts;

    @Mock
    private Request request;
    @Mock
    private Response response;
    @Mock
    private WallService wallService;

    @Before
    public void setup() {
        initMocksBehavior();
        wallApi = new WallApi(wallService);
    }

    private void initMocksBehavior() {
        initMocks(this);
        wallPosts = asList(new Post(POST_ID, USER_ID, TEXT, DATE_TIME));
        when(request.params("userId")).thenReturn(USER_ID);
        when(wallService.wallFor(USER_ID)).thenReturn(wallPosts);
    }

    @Test
    public void returnTheWallForAUser() {
        String result = wallApi.wallForUser(request, response);

        verify(response).status(200);
        verify(response).type("application/json");
        assertThat(result).isEqualTo(jsonWith(wallPosts));
    }

    private String jsonWith(List<Post> posts) {
        JsonArray json = new JsonArray();
        posts.forEach(post -> json.add(jsonObjectWith(post)));
        return json.toString();
    }

    private JsonObject jsonObjectWith(Post post) {
        return new JsonObject()
                .add("postId", post.getPostId())
                .add("userId", post.getUserId())
                .add("text", post.getText())
                .add("dateTime", formatter.format(post.getDateTime()));
    }
}
