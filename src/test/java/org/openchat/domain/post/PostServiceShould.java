package org.openchat.domain.post;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openchat.infrastructure.Clock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PostServiceShould {

    private static final String POST_ID = UUID.randomUUID().toString();
    private static final String USER_ID = UUID.randomUUID().toString();
    private static final String TEXT = "Hello";
    private static final LocalDateTime TODAY = LocalDateTime.of(2019, 3, 30, 22, 30, 0);
    private static final LocalDateTime YESTERDAY = LocalDateTime.of(2019, 3, 29, 22, 30, 0);
    private static final String INAPPROPRIATE_TEXT = "Hello elephant";

    private PostService postService;
    private Post todayPost;
    private Post yesterdayPost;
    private List<Post> posts;
    private List<Post> postsInReverseChronologicalOrder;

    @Mock
    private LanguageValidator languageValidator;
    @Mock
    private PostRepository postRepository;
    @Mock
    private Clock clock;
    @Mock
    private PostIdGenerator postIdGenerator;

    @Before
    public void setup() {
        initMocks(this);
        initMocksBehavior();
        postService = new PostService(postRepository, languageValidator, clock, postIdGenerator);
    }

    @Test(expected = InappropriateLanguageException.class)
    public void throwInappropriateLanguageExceptionWhenTextContainsInappropriateLanguage() {
        doThrow(new InappropriateLanguageException()).when(languageValidator).validate(INAPPROPRIATE_TEXT);

        postService.createPost(USER_ID, INAPPROPRIATE_TEXT);
    }

    @Test
    public void saveANewPost() {
        postService.createPost(USER_ID, TEXT);

        verify(postRepository).save(todayPost);
    }

    @Test
    public void returnNewlyCreatedPost() {
        Post result = postService.createPost(USER_ID, TEXT);

        assertThat(result).isEqualTo(todayPost);
    }

    @Test
    public void returnPostsByUserInReverseChronologicalOrder() {
        when(postRepository.postsBy(USER_ID)).thenReturn(posts);

        List<Post> result = postService.postsBy(USER_ID);

        assertThat(result).isEqualTo(postsInReverseChronologicalOrder);
    }

    private void initMocksBehavior() {
        todayPost = new Post(POST_ID, USER_ID, TEXT, TODAY);
        yesterdayPost = new Post(POST_ID, USER_ID, TEXT, YESTERDAY);
        posts = asList(todayPost, yesterdayPost);
        postsInReverseChronologicalOrder = asList(yesterdayPost, todayPost);
        when(clock.now()).thenReturn(TODAY);
        when(postIdGenerator.next()).thenReturn(POST_ID);
    }
}
