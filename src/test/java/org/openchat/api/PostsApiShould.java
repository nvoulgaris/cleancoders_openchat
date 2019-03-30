package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openchat.domain.post.InappropriateLanguageException;
import org.openchat.domain.post.Post;
import org.openchat.domain.post.PostService;
import spark.Request;
import spark.Response;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PostsApiShould {

    private static final String POST_ID = UUID.randomUUID().toString();
    private static final String USER_ID = UUID.randomUUID().toString();
    private static final String TEXT = "Hello";
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2019, 3, 30, 22, 30, 0);
    private static final String INAPPROPRIATE_TEXT = "Hello elephant";

    private PostsApi postsApi;
    private Post post;

    @Mock
    private Request request;
    @Mock
    private Response response;
    @Mock
    private PostService postService;

    @Before
    public void setup() {
        initMocks(this);
        initMocksBehavior();
        postsApi = new PostsApi(postService);
    }

    @Test
    public void returnAnErrorWhenPostContainsInappropriateLanguage() {
        mockPostContainsInappropriateLanguage();

        String result = postsApi.create(request, response);

        verify(response).status(400);
        assertThat(result).isEqualTo("Post contains inappropriate language.");
    }

    @Test
    public void returnNewlyCreatedPost() {
        mockValidPost();

        String result = postsApi.create(request, response);

        verify(response).status(201);
        verify(response).type("application/json");
        assertThat(result).isEqualTo(jsonWith(post));
    }

    private void initMocksBehavior() {
        post = new Post(POST_ID, USER_ID, TEXT, DATE_TIME);
        when(request.params("userId")).thenReturn(USER_ID);
    }

    private void mockPostContainsInappropriateLanguage() {
        when(request.body()).thenReturn(jsonWith(INAPPROPRIATE_TEXT));
        when(postService.createPost(USER_ID, INAPPROPRIATE_TEXT)).thenThrow(new InappropriateLanguageException());
    }

    private void mockValidPost() {
        when(request.body()).thenReturn(jsonWith(TEXT));
        when(postService.createPost(USER_ID, TEXT)).thenReturn(post);
    }

    private String jsonWith(String text) {
        return new JsonObject()
                .add("text", text)
                .toString();
    }

    private String jsonWith(Post post) {
        return new JsonObject()
                .add("postId", post.getPostId())
                .add("userId", post.getUserId())
                .add("text", post.getText())
                .add("dateTime", post.getDateTime().toString())
                .toString();
    }
}
