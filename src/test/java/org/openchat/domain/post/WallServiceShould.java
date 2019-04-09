package org.openchat.domain.post;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

public class WallServiceShould {

    private static final String FOLLOWER_ID = UUID.randomUUID().toString();
    private static final String USER_ID_1 = UUID.randomUUID().toString();
    private static final String USER_ID_2 = UUID.randomUUID().toString();
    private static final String USERNAME_1 = "a username";
    private static final String USERNAME_2 = "a second username";
    private static final String PASSWORD = "a password";
    private static final String ABOUT = "about me";
    private static final String POST_ID_1 = UUID.randomUUID().toString();
    private static final String POST_ID_2 = UUID.randomUUID().toString();
    private static final String POST_ID_3 = UUID.randomUUID().toString();
    private static final String TEXT = "Hello";
    private static final LocalDateTime TODAY = LocalDateTime.of(2019, 3, 30, 22, 30, 0);
    private static final LocalDateTime YESTERDAY = LocalDateTime.of(2019, 3, 29, 22, 30, 0);
    private static final LocalDateTime BEFORE_YESTERDAY = LocalDateTime.of(2019, 3, 28, 22, 30, 0);

    private WallService wallService;
    private List<User> followees;
    private List<String> followeesIds;
    private List<Post> followeesPosts;
    private List<Post> followeesPostsInReverseChronologicalOrder;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;

    @Before
    public void setup() {
        initMocks(this);
        initMocksBehavior();
        wallService = new WallService(userRepository, postRepository);
    }

    @Test
    public void returnThePostsInTheUsersWallInReverseChronologicalOrder() {
        when(userRepository.followeesFor(FOLLOWER_ID)).thenReturn(followees);
        when(postRepository.postsBy(followeesIds)).thenReturn(followeesPosts);

        List<Post> result = wallService.wallFor(FOLLOWER_ID);

        assertThat(result).containsExactlyElementsOf(followeesPostsInReverseChronologicalOrder);
    }

    private void initMocksBehavior() {
        User followee1 = new User(USER_ID_1, USERNAME_1, PASSWORD, ABOUT);
        User followee2 = new User(USER_ID_2, USERNAME_2, PASSWORD, ABOUT);
        Post post1 = new Post(POST_ID_1, USERNAME_1, TEXT, YESTERDAY);
        Post post2 = new Post(POST_ID_2, USERNAME_2, TEXT, TODAY);
        Post post3 = new Post(POST_ID_3, USERNAME_2, TEXT, BEFORE_YESTERDAY);
        followees = asList(followee1, followee2);
        followeesIds = asList(followee1.getId(), followee2.getId(), FOLLOWER_ID);
        followeesPosts = asList(post2, post1, post3);
        followeesPostsInReverseChronologicalOrder = asList(post2, post1, post3);
    }
}
